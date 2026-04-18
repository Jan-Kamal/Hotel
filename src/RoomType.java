import java.util.List;

public class RoomType {
    private String typeName;
    private List<Amenity> baseAmenities;

    public RoomType(String typeName) {
        this.typeName = typeName;
    }

   

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public List<Amenity> getBaseAmenities() { return baseAmenities; }

}