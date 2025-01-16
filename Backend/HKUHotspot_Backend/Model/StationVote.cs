namespace HKUHotspot_Backend.Model
{
    public class StationVote
    {
        public int Id { get; set; }
        public int StationId { get; set; }
        public int HourOfVote { get; set; }
        public int MinuteOfVote { get; set; }
        public int Vote {  get; set; }

    }
}
