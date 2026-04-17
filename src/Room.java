public class Room {
    private String roomNumber;
    private RoomType roomType;
    private double price;
    private boolean isAvailable;
    private String viewPreference;

    public Room(String roomNumber, RoomType roomType, double price, String viewPreference) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.viewPreference = viewPreference;
        this.isAvailable = true;
    }
    public void setPrice(double price)
    {    
    this.price = price;
    }
    public double getPrice() { return price; }
   public String getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean status) { this.isAvailable = status; }
    public String getViewPreference() { return viewPreference; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + roomType.getTypeName() + " | View: " + viewPreference + "] - Price: $" + price + "\n   Base Amenities: " + roomType.getBaseAmenities();
    }
        public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
}
}