import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

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

public static void registerNewGuest(Scanner scanner) {
    System.out.println("Please enter your name:");
    String name = scanner.nextLine();

    System.out.println("Enter your password:");
    String p = scanner.nextLine();

    System.out.println("Your Initial Balance:");
    double bal = scanner.nextDouble();
    scanner.nextLine();

    System.out.println("Address:");
    String addr = scanner.nextLine();

   
    Guest newGuest = Guest.register(name, p, java.time.LocalDate.now(), bal, addr, Guest.Gender.MALE);
    
    
    guests.add(newGuest);
    
    System.out.println("System: Guest added to database successfully.");
    
}
}

