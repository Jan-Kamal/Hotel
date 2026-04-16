import java.time.LocalDate;
import java.util.List;


public class Admin extends Staff {

    public Admin(String username, String password,
                 LocalDate dateOfBirth, int workingHours) {
       
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
    }

   
    @Override
    public void performDuties() {
        System.out.println("Admin " + getUsername() + " is managing rooms, amenities, and room types.");
    }

  
    public RoomType createRoomType(String typeName) {
        RoomType rt = new RoomType(typeName);
        System.out.println("Admin created room type: " + typeName);
        return rt;
    }

    

    public void updateRoomType(RoomType roomType, String newName) {
        System.out.println("Updating room type from '" + roomType.getTypeName() + "' to '" + newName + "'");
        roomType.setTypeName(newName);
    }

    // DELETE
    public void deleteRoomType(List<RoomType> roomTypeList, RoomType roomType) {
        roomTypeList.remove(roomType);
        System.out.println("Deleted room type: " + roomType.getTypeName());
    }

    public Amenity createAmenity(String amenityName) {
        Amenity a = new Amenity(amenityName);
        System.out.println("Admin created amenity: " + amenityName);
        return a;
    }

    
    public void readAmenity(Amenity amenity) {
        System.out.println("Amenity Info: " + amenity);
    }


    public void updateAmenity(Amenity amenity, String newName) {
        System.out.println("Updating amenity from '" + amenity.getName() + "' to '" + newName + "'");
        amenity.setName(newName);
    }

   
    public void deleteAmenity(List<Amenity> amenityList, Amenity amenity) {
        amenityList.remove(amenity);
        System.out.println("Deleted amenity: " + amenity.getName());
    }
}