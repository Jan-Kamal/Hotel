import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Guest  {

    public enum Gender {
        MALE, FEMALE
    }

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;
    private List<String> roomPreferences;

    public Guest(String username, String password, LocalDate dateOfBirth,
                 double balance, String address, Gender gender) {
        this.username = username;
        this.password = validatePassword(password);
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.address = address;
        this.gender = gender;
        this.roomPreferences = new ArrayList<>();
    }

    private String validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters.");
        }
        return password;
    }


    
    public static Guest register(String username, String password, LocalDate dob,
                                  double balance, String address, Gender gender) {
        System.out.println("Guest registered: " + username);
        return new Guest(username, password, dob, balance, address, gender);
    }


    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            System.out.println("Login successful. Welcome, " + this.username + "!");
            return true;
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    public void viewAvailableRooms() {
        System.out.println(username + " is viewing available rooms...");
        
    }

    
    public void makeReservation(String roomNumber, LocalDate checkIn, LocalDate checkOut) {
        System.out.println(username + " made a reservation for room " + roomNumber
                + " from " + checkIn + " to " + checkOut);
        
    }

    
   public boolean canAfford(double amount) {
        return this.balance >= amount;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }


    public void addRoomPreference(String preference) {
        roomPreferences.add(preference);
    }
    public void topUpBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Successfully added $" + amount + ". New balance: $" + this.balance);
        }
    }



    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public List<String> getRoomPreferences() { return roomPreferences; }

    public void setPassword(String password) { this.password = validatePassword(password); }

    @Override
    public String toString() {
        return "Guest{" +
                "username='" + username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", balance=" + balance +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", roomPreferences=" + roomPreferences +
                '}';
    }
   public void requestExtraAmenities(Scanner scanner) {
    System.out.println("\n--- Request Extra Amenities ---");
    
    
    ArrayList<Reservation> guestActiveRes = new ArrayList<>();
    for (Reservation res : HotelDatabase.reservations) {
        
        if (res.getGuest().equals(this) && 
            res.getStatus() != Reservation.ReservationStatus.CANCELLED) {
            guestActiveRes.add(res);
        }
    }

    if (guestActiveRes.isEmpty()) {
        System.out.println("You have no active reservations to upgrade.");
        return; 
    }

    
    Reservation activeRes = null;
    if (guestActiveRes.size() == 1) {
        activeRes = guestActiveRes.get(0);
        System.out.println("Upgrading Room: " + activeRes.getRoom().getRoomNumber());
    } else {
        System.out.println("You have multiple reservations. Which one do you want to upgrade?");
        for (int i = 0; i < guestActiveRes.size(); i++) {
            Reservation r = guestActiveRes.get(i);
            System.out.println(i + ". Room " + r.getRoom().getRoomNumber() + " (" + r.getRoom().getRoomType().getTypeName() + ")");
        }
        System.out.print("Choice: ");
        int choice = scanner.nextInt(); scanner.nextLine();
        
        if (choice >= 0 && choice < guestActiveRes.size()) {
            activeRes = guestActiveRes.get(choice);
        } else {
            System.out.println("Invalid selection.");
            return;
        }
    }

    
    boolean isSuite = activeRes.getRoom().getRoomType().getTypeName().toLowerCase().contains("suite");
    
    System.out.println("1. Add Extra Bed");
    if (!isSuite) System.out.println("2. Add Unlimited WiFi (+$50)");
    System.out.print("Choice: ");
    int amChoice = scanner.nextInt(); scanner.nextLine();

    if (amChoice == 1) {
        double bedPrice = isSuite ? 50.0 : 100.0; 
        if (this.canAfford(bedPrice)) { 
            this.deductBalance(bedPrice);
            activeRes.addExtraAmenity(new Amenity("Extra Bed"));
            System.out.println("Extra Bed added! Charged $" + bedPrice);
        } else { System.out.println("Insufficient funds!"); }
    } else if (amChoice == 2 && !isSuite) {
        if (this.canAfford(50.0)) {
            this.deductBalance(50.0);
            activeRes.addExtraAmenity(new Amenity("Unlimited WiFi"));
            System.out.println("Unlimited WiFi added! Charged $50.0");
        } else { System.out.println("Insufficient funds!"); }
    }
}
}
