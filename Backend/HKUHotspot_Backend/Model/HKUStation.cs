namespace HKUHotspot_Backend.Model
{
    public class HKUStation
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public int CurrentOccupancy { get; set; } 
        public float StartPositionX { get; set; }
        public float StartPositionY { get; set;}
        public float EndPositionX { get; set;}
        public float EndPositionY { get; set;}
    }
}
