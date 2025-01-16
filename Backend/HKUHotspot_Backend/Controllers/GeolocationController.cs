using HKUHotspot_Backend.Database;
using HKUHotspot_Backend.Model;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace HKUHotspot_Backend.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class GeolocationController : ControllerBase
    {
        private readonly HKUHotspotDBContext _dbContext;

        public GeolocationController(HKUHotspotDBContext dBContext)
        {
            _dbContext = dBContext;
        }

        // api/Geolocation/all_positions
        [HttpGet("all_positions")]
        public async Task<ActionResult<IEnumerable<Position>>> GetPositionsOfAllUsers()
        {
            var positionList = await _dbContext.HKUUsers
                .Select(user => new Position 
                {
                    X = user.PositionX,
                    Y = user.PositionY
                } )
                .ToListAsync();

            return Ok(positionList);
        }

        [HttpPost("send_position/{email_address}/{X}/{Y}")]
        public async Task<IActionResult> SendPosition(string email_address, float X, float Y)
        {
            var user = await _dbContext.HKUUsers.FindAsync(email_address);
            if(user == null)
            {
                return NotFound();
            }
            else
            {
                // TODO: Check that the positions are in campus, otherwise dont send it
                user.PositionX = X; 
                user.PositionY = Y;
                try
                {
                    await _dbContext.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException) when (!UserExists(email_address))
                {
                    return NotFound();
                }

                return NoContent();
            }
        }

        private bool UserExists(string emailAddress)
        {
            return _dbContext.HKUUsers.Any(e => e.Email == emailAddress);
        }
    }
}
