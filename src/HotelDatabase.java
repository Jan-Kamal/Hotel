import java.time.LocalDate;
import java.util.ArrayList;

public class HotelDatabase {
    public static ArrayList<Guest> guests = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Receptionist> receptionists = new ArrayList<>();
    public static ArrayList<Room> rooms = new ArrayList<>(); 
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<RoomType> roomTypes = new ArrayList<>();

    public static void initializeData() {
       
        admins.add(new Admin("mark_admin", "password123", LocalDate.of(2000, 1, 1), 8));
        
        
        guests.add(new Guest("m_guest", "password1234", LocalDate.of(2005, 3, 15), 5000.0, "Cairo", Guest.Gender.MALE));
            

        receptionists.add(new Receptionist("staff_john", "staff123", LocalDate.of(1995, 5, 10), 8));
}
}