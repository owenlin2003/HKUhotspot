using HKUHotspot_Backend.Model;
using Microsoft.EntityFrameworkCore;

namespace HKUHotspot_Backend.Database
{
    public class HKUHotspotDBContext : DbContext
    {
        public HKUHotspotDBContext(DbContextOptions<HKUHotspotDBContext> options) : base(options)
        {

        }

        public DbSet<HKUUser> HKUUsers { get; set; }
        public DbSet<HKUStation> HKUStation { get; set; }
        public DbSet<HKUStationComments> HKUStationComments { get; set; }
        public DbSet<StationVote> StationVotes { get; set; }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            //optionsBuilder.UseSqlServer("Server=(localdb)\\HKUHotspotDB;Database=HKU_DB;Trusted_Connection=True;");
            base.OnConfiguring(optionsBuilder);
        }
    }
}
