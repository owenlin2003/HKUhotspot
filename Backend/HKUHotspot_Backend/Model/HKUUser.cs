using System.ComponentModel.DataAnnotations;

namespace HKUHotspot_Backend.Model
{
    public class HKUUser
    {
        [Key]
        public string Email { get; set; }
        public string UserName { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Password { get; set; }
        public float PositionX { get; set; }
        public float PositionY { get; set; }
    }
}
