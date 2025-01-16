using Microsoft.EntityFrameworkCore;

namespace HKUHotspot_Backend.Model
{
    public class HKUStationComments
    {
        public int Id { get; set; }
        public int StationID { get; set; }
        public string CommentAuthor { get; set; }
        public string CommentDescription { get; set; }
    }
}
