import java.time.LocalDate;
import java.util.List;

// ══════════════════════════════════════════════════════
//  ADMIN CLASS — extends Staff (inherits all Staff fields)
//  Responsible for full CRUD on rooms, amenities, room types
// ══════════════════════════════════════════════════════
public class Admin extends Staff {

    // ── Constructor ───────────────────────────────────
    public Admin(String username, String password,
                 LocalDate dateOfBirth, int workingHours) {
        // calls the Staff constructor, role is fixed to ADMIN
        super(username, password, dateOfBirth, Role.ADMIN, workingHours);
    }

    // ── Must implement abstract method from Staff ─────
    @Override
    public void performDuties() {
        System.out.println("Admin " + getUsername() + " is managing rooms, amenities, and room types.");
    }

    // ══════════════════════════════════════════════════
    //  ROOM TYPE CRUD
    // ══════════════════════════════════════════════════

    // CREATE
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

    // ══════════════════════════════════════════════════
    //  AMENITY CRUD
    // ══════════════════════════════════════════════════

    // CREATE
    public Amenity createAmenity(String amenityName) {
        Amenity a = new Amenity(amenityName);
        System.out.println("Admin created amenity: " + amenityName);
        return a;
    }

    // READ
    public void readAmenity(Amenity amenity) {
        System.out.println("Amenity Info: " + amenity);
    }

    // UPDATE
    public void updateAmenity(Amenity amenity, String newName) {
        System.out.println("Updating amenity from '" + amenity.getName() + "' to '" + newName + "'");
        amenity.setName(newName);
    }

    // DELETE
    public void deleteAmenity(List<Amenity> amenityList, Amenity amenity) {
        amenityList.remove(amenity);
        System.out.println("Deleted amenity: " + amenity.getName());
    }
}