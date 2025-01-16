using HKUHotspot_Backend.Database;
using Microsoft.EntityFrameworkCore;

namespace HKUHotspot_Backend.Services
{
    public class HourlyTaskService : BackgroundService
    {
        private readonly IServiceScopeFactory _scopeFactory;

        public HourlyTaskService(IServiceScopeFactory scopeFactory)
        {
            _scopeFactory = scopeFactory;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            while (!stoppingToken.IsCancellationRequested)
            {
                // Create a scope to resolve scoped services
                using (var scope = _scopeFactory.CreateScope())
                {
                    var dbContext = scope.ServiceProvider.GetRequiredService<HKUHotspotDBContext>();
                    await DeleteAllVotes(dbContext);
                }

                await Task.Delay(TimeSpan.FromHours(1), stoppingToken);
            }
        }

        private async Task DeleteAllVotes(HKUHotspotDBContext dbContext)
        {
            var currentHour = DateTime.Now.Hour;

            // Filter and delete votes
            var votesToDelete = await dbContext.StationVotes
                .Where(vote => currentHour >= (vote.HourOfVote + 2) % 24)
                .ToListAsync();

            dbContext.StationVotes.RemoveRange(votesToDelete);
            await dbContext.SaveChangesAsync();
        }
    }
}
