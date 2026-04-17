import java.time.LocalDate;
import java.util.Scanner;


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
public void viewAllData() {
    System.out.println("\n========= SYSTEM DATA REPORT =========");

    // 1. VIEW ALL GUESTS
    System.out.println("\n--- REGISTERED GUESTS ---");
    if (HotelDatabase.guests.isEmpty()) {
        System.out.println("No guests registered yet.");
    } else {
        for (Guest g : HotelDatabase.guests) {
            System.out.println("Name: " + g.getUsername() + " | Balance: $" + g.getBalance() + " | Address: " + g.getAddress());
        }
    }

    // 2. VIEW ALL ROOMS
    System.out.println("\n--- HOTEL ROOMS ---");
    if (HotelDatabase.rooms.isEmpty()) {
        System.out.println("No rooms created yet.");
    } else {
        for (Room r : HotelDatabase.rooms) {
            System.out.println("Room #" + r.getRoomNumber() + " | Type: " + r.getRoomType().getTypeName() + 
                               " | Price: $" + r.getPrice() + " | Status: " + (r.isAvailable() ? "Available" : "Occupied"));
        }
    }

    // 3. VIEW ALL RESERVATIONS
    System.out.println("\n--- ACTIVE RESERVATIONS ---");
    if (HotelDatabase.reservations.isEmpty()) {
        System.out.println("No reservations made yet.");
    } else {
        for (Reservation res : HotelDatabase.reservations) {
            System.out.println(res); // This uses the toString() you already have in Reservation
        }
    }
    System.out.println("\n======================================");
}
public void updateRoomPrice(String roomNum, double newPrice) {
    for (Room r : HotelDatabase.rooms) {
        if (r.getRoomNumber().equalsIgnoreCase(roomNum)) {
            r.setPrice(newPrice);
            System.out.println("Success: Room " + roomNum + " price updated to $" + newPrice);
            return;
        }
    }
    System.out.println("Error: Room not found.");
}

public void deleteRoom(String roomNum) {
    Room toRemove = null;
    for (Room r : HotelDatabase.rooms) {
        if (r.getRoomNumber().equalsIgnoreCase(roomNum)) {
            toRemove = r;
            break;
        }
    }
    if (toRemove != null) {
        HotelDatabase.rooms.remove(toRemove);
        System.out.println("Success: Room " + roomNum + " deleted from system.");
    } else {
        System.out.println("Error: Room not found.");
    }
}
public void updateRoomType(String roomNum, RoomType newType) {
    for (Room r : HotelDatabase.rooms) {
        if (r.getRoomNumber().equalsIgnoreCase(roomNum)) {
            r.setRoomType(newType);
            System.out.println("Success: Room " + roomNum + " is now a " + newType.getTypeName());
            return;
        }
    }
}
public void handleRoomTypeUpdate(Scanner scanner) {
    // 1. Check if there are even any rooms to update
    if (HotelDatabase.rooms.isEmpty()) {
        System.out.println("Error: There are no rooms in the system to change.");
        return; 
    }

    System.out.print("Enter Room Number to change its type: ");
    String rNum = scanner.nextLine();
    
    // Find the room first to see what its CURRENT type is
    Room foundRoom = null;
    for (Room r : HotelDatabase.rooms) {
        if (r.getRoomNumber().equalsIgnoreCase(rNum)) {
            foundRoom = r;
            break;
        }
    }

    if (foundRoom == null) {
        System.out.println("Error: Room not found.");
        return;
    }

    // 2. Show available types EXCLUDING the current one
    System.out.println("Available New Types (Current: " + foundRoom.getRoomType().getTypeName() + "):");
    boolean otherTypesExist = false;
    for (RoomType rt : HotelDatabase.roomTypes) {
        // ONLY show types that are different from the current one
        if (!rt.getTypeName().equalsIgnoreCase(foundRoom.getRoomType().getTypeName())) {
            System.out.println("- " + rt.getTypeName());
            otherTypesExist = true;
        }
    }

    if (!otherTypesExist) {
        System.out.println("No other room types available to switch to.");
        return;
    }

    System.out.print("Enter the NEW type name: ");
    String newTypeName = scanner.nextLine();
    
    RoomType selectedType = null;
    for (RoomType rt : HotelDatabase.roomTypes) {
        if (rt.getTypeName().equalsIgnoreCase(newTypeName)) {
            selectedType = rt;
            break;
        }
    }
    
    if (selectedType != null) {
        foundRoom.setRoomType(selectedType);
        System.out.println("Success: Room " + rNum + " is now a " + selectedType.getTypeName());
    } else {
        System.out.println("Error: That Room Type does not exist!");
    }
}
}