using HKUHotspot_Backend.Database;
using HKUHotspot_Backend.Model;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;

namespace HKUHotspot_Backend.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class AccountController : ControllerBase
    {
        private readonly HKUHotspotDBContext _dbContext;

        public AccountController(HKUHotspotDBContext dBContext)
        {
            _dbContext = dBContext;
        }

        [HttpPost("add_user")]
        public async Task<IActionResult> AddUser(HKUUser newUser)
        {
            var userToDelete = await _dbContext.HKUUsers.FindAsync(newUser.Email); // Find the user to delete in case a similar user exists
            if (userToDelete == null)
            {
                _dbContext.HKUUsers.Add(newUser); // Add the new user to the DbSet in your DbContext
                await _dbContext.SaveChangesAsync(); // Save changes to the database
                return NoContent();
            }
            else
            {
                //TODO: Return a error message that an account with that email address already exist.
                return BadRequest();
            }
        }

        [HttpGet("get_user/{email_address}/{password}")]
        public async Task<ActionResult<HKUUser>> GetUser(string email_address, string password)
        {
            var user = await _dbContext.HKUUsers.FindAsync(email_address);
            if (user == null)
            {
                return NoContent();
            }
            else
            {
                string real_password = user.Password;
                if (real_password == password)
                {
                    return Ok(user);
                }
                else
                {
                    
                    return Unauthorized();
                    
                }

            }
        }

        [HttpPost("delete_user/{email_address}")]
        public async Task<IActionResult> DeleteUser(string email_address)
        {
            var userToDelete = await _dbContext.HKUUsers.FindAsync(email_address); // Find the user to delete
            if (userToDelete != null)
            {
                _dbContext.HKUUsers.Remove(userToDelete); // Remove the user from the DbSet
                await _dbContext.SaveChangesAsync(); // Save changes to the database
            }
            return NoContent();
        }

        [HttpPost("change_username/{email_address}/{new_username}")]
        public async Task<IActionResult> ChangeUsername(string email_address, string new_username)
        {
            var user = await _dbContext.HKUUsers.FindAsync(email_address);
            if (user == null)
            {
                return NotFound();
            }
            else
            {
                user.UserName = new_username;
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

        [HttpPost("change_password/{email_address}/{new_password}")]
        public async Task<IActionResult> ChangePassword(string email_address, string new_password)
        {
            var user = await _dbContext.HKUUsers.FindAsync(email_address);
            if (user == null)
            {
                return NotFound();
            }
            else
            {
                user.Password = new_password;
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

        [HttpGet("show_user")]
        public async Task<IActionResult> ShowAllUsers()
        {
            var userList = await _dbContext.HKUUsers.ToListAsync();

            return Ok(userList);
        }

        private bool UserExists(string emailAddress)
        {
            return _dbContext.HKUUsers.Any(e => e.Email == emailAddress);
        }

    }
}
