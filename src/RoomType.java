import java.util.ArrayList;
import java.util.List;

public class RoomType {
    private String typeName;
    private List<Amenity> baseAmenities;

    public RoomType(String typeName) {
        this.typeName = typeName;
        this.baseAmenities = new ArrayList<>();
        assignBaseAmenities();
    }

    private void assignBaseAmenities() {
        String lower = typeName.toLowerCase();
        if (lower.contains("single")) {
            baseAmenities.add(new Amenity("47inch Smart TV"));
            baseAmenities.add(new Amenity("Mini bar"));
        } else if (lower.contains("double")) {
            baseAmenities.add(new Amenity("47inch Smart TV"));
            baseAmenities.add(new Amenity("Mini bar"));
            baseAmenities.add(new Amenity("Extra Bed"));
        } else if (lower.contains("triple")) {
            baseAmenities.add(new Amenity("3 Beds"));
            baseAmenities.add(new Amenity("50inch TV"));
        } else if (lower.contains("suite")) {
            baseAmenities.add(new Amenity("King size bed"));
            baseAmenities.add(new Amenity("Unlimited WiFi"));
            baseAmenities.add(new Amenity("mini bar"));
        } else {
            baseAmenities.add(new Amenity("Standard Amenities"));
        }
    }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public List<Amenity> getBaseAmenities() { return baseAmenities; }
}