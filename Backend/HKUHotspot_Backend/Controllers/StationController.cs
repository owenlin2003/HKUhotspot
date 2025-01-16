using HKUHotspot_Backend.Database;
using HKUHotspot_Backend.Model;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Net;

namespace HKUHotspot_Backend.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class StationController : ControllerBase
    {
        private readonly HKUHotspotDBContext _dbContext;

        public StationController(HKUHotspotDBContext dBContext)
        {
            _dbContext = dBContext;
        }

        [HttpGet("get_station_occupancy/{id}")]
        public async Task<ActionResult<int>> GetStationOccupancy(int id)
        {
            var votes = await _dbContext.StationVotes.Where(s => s.StationId == id).ToListAsync();
            if (votes == null || votes.Count() == 0)
            {
                return 3; //Default value
            }
            else
            {
                var sum = 0;
                foreach (var vote in votes)
                {
                    sum += vote.Vote;
                }
                int average = sum / votes.Count();

                return Ok(average);
            }
        }

        [HttpPost("submit_vote/{id}/{vote}")]
        public async Task<IActionResult> SubmitVote(int id, int vote)
        {
            int currentHour = DateTime.Now.Hour;
            int currentMinute = DateTime.Now.Minute;
            var stationVote = new StationVote() { HourOfVote = currentHour, 
                MinuteOfVote = currentMinute, StationId = id, Vote = vote };

            _dbContext.StationVotes.Add(stationVote);
            await _dbContext.SaveChangesAsync();

            int statusCode = (int)HttpStatusCode.OK;
            string errorMessage = "Vote submitted";
            return StatusCode(statusCode, errorMessage);
        }

        [HttpGet("get_all_comments/{id}")]
        public async Task<ActionResult<List<string>>> GetAllComments(int id)
        {
            var stationComments = await _dbContext.HKUStationComments.Where(c => c.StationID == id).ToListAsync();
            return Ok(stationComments);
        }

        [HttpPost("post_comment/{id}/{username}/{comment}")]
        public async Task<IActionResult> PostComment(int id, string username, string comment)
        {
            HKUStationComments new_comment = new HKUStationComments();
            new_comment.StationID = id;
            new_comment.CommentDescription = comment;
            new_comment.CommentAuthor = username;
            _dbContext.HKUStationComments.Add(new_comment);
            await _dbContext.SaveChangesAsync();
            int statusCode = (int)HttpStatusCode.OK;
            string errorMessage = "Comment posted";
            return StatusCode(statusCode, errorMessage);
        }
    }
}
