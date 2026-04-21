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
    public static ArrayList<Amenity>amenitys = new ArrayList<>();

    public static void initializeData() {
       
        admins.add(new Admin("mark_admin", "password123", LocalDate.of(2000, 1, 1), 8));
        
        receptionists.add(new Receptionist("staff_john", "staff123", LocalDate.of(1995, 5, 10), 8));
         try {
        guests.add(new Guest("m_guest", "password1234", LocalDate.of(2005, 3, 15), 5000.0, "Cairo", Guest.Gender.MALE));
    } catch (NegativeBalanceException e) {
        System.out.println("Init error: " + e.getMessage());
    }
}

public static void registerNewGuest(Scanner scanner)throws NegativeBalanceException {
    System.out.println("Please enter your name:");
    String name = scanner.nextLine();

    System.out.println("Enter your password:");
    String p = scanner.nextLine();

    System.out.println("Your Initial Balance:");
    double bal = scanner.nextDouble();
    scanner.nextLine();

    System.out.println("Address:");
    String addr = scanner.nextLine();


    Guest.Gender gender = null;
    while (gender == null) {
        System.out.println("Gender (1. MALE / 2. FEMALE):");
        String gChoice = scanner.nextLine().trim();
        if (gChoice.equals("1") || gChoice.equalsIgnoreCase("MALE")) {
            gender = Guest.Gender.MALE;
        } else if (gChoice.equals("2") || gChoice.equalsIgnoreCase("FEMALE")) {
            gender = Guest.Gender.FEMALE;
        } else {
            System.out.println("[ERROR]: Please enter 1 for MALE or 2 for FEMALE.");
        }
    }

    Guest newGuest = new Guest(name, p, LocalDate.now(), bal, addr, gender);
    guests.add(newGuest);
    System.out.println("Registration successful! Welcome, " + name + "!");

}
}

