import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        HotelDatabase.initializeData();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- AIN SHAMS HOTEL MANAGEMENT SYSTEM ---");
            System.out.println("1. Login as Guest");
            System.out.println("2.Register Guest");
            System.out.println("3. Login as Admin");
            System.out.println("4. Login as Receptionist");
            System.out.println("5. Exit System");
            System.out.print("Choice: ");

            if (!scanner.hasNextInt()) { scanner.next(); continue; }
            int choice = scanner.nextInt(); scanner.nextLine(); 

            if (choice == 5) break;

           

            if (choice == 1) {
                System.out.print("Username: ");
                  String user = scanner.nextLine();
                 System.out.print("Password: ");
                 String pass = scanner.nextLine();
                handleGuest(user, pass, scanner);}
            if(choice==2) try {HotelDatabase.registerNewGuest(scanner);}
            catch(IllegalArgumentException e){
                System.out.println("\n[ERROR]: " + e.getMessage());
                 System.out.println("Please try registering again with a valid password.");
            }
            else if (choice == 3)
                { 
                    System.out.print("Username: ");
                     String user = scanner.nextLine();
                     System.out.print("Password: ");
                     String pass = scanner.nextLine();
                    handleAdmin(user, pass, scanner);}
            else if (choice == 4){
                System.out.print("Username: ");
                String user = scanner.nextLine();
                System.out.print("Password: ");
                String pass = scanner.nextLine();
                 handleReceptionist(user, pass, scanner);}
        }
    }

    private static void handleGuest(String user, String pass, Scanner scanner) {
        
        Guest currentGuest = null;
        for (Guest g : HotelDatabase.guests) {
            if (g.login(user, pass)) { currentGuest = g; break; }
        }
        if (currentGuest == null) return;

        while (true) {
            System.out.println("\n--- Guest Menu (" + currentGuest.getUsername() + ") | Balance: $" + currentGuest.getBalance() + " ---");
            System.out.println("1. Search & Reserve Rooms\n2. View/Cancel My Reservations\n3. Top-Up Balance\n4. Request Extra Amenities\n5. Logout");
            int act = scanner.nextInt(); scanner.nextLine();

            if (act == 5) break;

         
               if (act == 1) { 
    System.out.print("Preferred Room Type (e.g., Suite, Single): ");
    String typePref = scanner.nextLine();
    System.out.print("Preferred View (e.g., Sea View, Pool View): ");
    String viewPref = scanner.nextLine();

  

    if (!currentGuest.getRoomPreferences().contains(viewPref)) {
        currentGuest.getRoomPreferences().add(viewPref);
    }

    boolean autoReserved = false;
    
    
    for (Room r : HotelDatabase.rooms) {
        if (r.isAvailable() && 
            r.getRoomType().getTypeName().equalsIgnoreCase(typePref) && 
            r.getViewPreference().equalsIgnoreCase(viewPref)) {
            
            System.out.println("\n>>> PERFECT MATCH FOUND! <<<");
            
            if (currentGuest.canAfford(r.getPrice())) {
                // Get Payment Method
                System.out.println("Select Payment Method: 1.CASH 2.CREDIT_CARD 3.ONLINE");
                int pChoice = scanner.nextInt(); scanner.nextLine();
                Invoice.PaymentMethod m = (pChoice == 1) ? Invoice.PaymentMethod.CASH : 
                                         (pChoice == 2) ? Invoice.PaymentMethod.CREDIT_CARD : 
                                         Invoice.PaymentMethod.ONLINE;

                
                LocalDate checkIn = null;
                LocalDate checkOut = null;
                while (true) {
                    checkIn = LocalDate.parse(Receptionist.askForDate("Check In", scanner));
                    checkOut = LocalDate.parse(Receptionist.askForDate("Check Out", scanner));

                    if (checkOut.isAfter(checkIn)) break; 
                    System.out.println("\n[ERROR]: Check-out must be after Check-in! Try again.");
                }

                
                currentGuest.deductBalance(r.getPrice());
                Invoice inv = new Invoice(r.getPrice(), m);
                Reservation res = new Reservation(currentGuest, r, checkIn, checkOut);
                
                HotelDatabase.reservations.add(res);
                r.setAvailable(false); 
                
                System.out.println("\n--- AUTO-RESERVATION CONFIRMED ---");
                inv.printReceipt();
                autoReserved = true;
            } else {
                System.out.println("Match found, but you have insufficient funds.");
            }
            break; 
        }
    }

    
    if (!autoReserved) {
        System.out.println("\nNo exact match could be auto-reserved. Available options:");
        for (Room r : HotelDatabase.rooms) {
            if (r.isAvailable()) System.out.println(r);
        }
        
        System.out.print("Enter Room Number to manually reserve (or 'exit'): ");
        String rNum = scanner.nextLine();
        if (rNum.equalsIgnoreCase("exit")) return;

        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber().equalsIgnoreCase(rNum) && r.isAvailable()) {
                if (currentGuest.canAfford(r.getPrice())) {
                    
                    System.out.println("Select Payment Method: 1.CASH 2.CREDIT_CARD 3.ONLINE");
                    int pChoice = scanner.nextInt(); scanner.nextLine();
                    Invoice.PaymentMethod m = (pChoice == 1) ? Invoice.PaymentMethod.CASH : 
                                             (pChoice == 2) ? Invoice.PaymentMethod.CREDIT_CARD : 
                                             Invoice.PaymentMethod.ONLINE;

                    
                    LocalDate checkIn = null;
                    LocalDate checkOut = null;
                    while (true) {
                        checkIn = LocalDate.parse(Receptionist.askForDate("Check In", scanner));
                        checkOut = LocalDate.parse(Receptionist.askForDate("Check Out", scanner));

                        if (checkOut.isAfter(checkIn)) break;
                        System.out.println("\n[ERROR]: Check-out must be after Check-in! Try again.");
                    }

                    currentGuest.deductBalance(r.getPrice());
                    Invoice inv = new Invoice(r.getPrice(), m);
                    Reservation res = new Reservation(currentGuest, r, checkIn, checkOut);
                    
                    HotelDatabase.reservations.add(res);
                    r.setAvailable(false); 
                    
                    System.out.println("\n--- MANUAL RESERVATION CONFIRMED ---");
                    inv.printReceipt();
                } else {
                    System.out.println("Insufficient funds!");
                }
                break;
            }
        }
    }
}
else if (act == 2) { 
    if (HotelDatabase.reservations.isEmpty()) {
        System.out.println("No reservations found in the system.");
    } else {
        
        ArrayList<Reservation> myRes = new ArrayList<>();
        for (Reservation res : HotelDatabase.reservations) {
            if (res.getGuest().equals(currentGuest)) {
                myRes.add(res);
            }
        }

        if (myRes.isEmpty()) {
            System.out.println("You have no active reservations.");
        } else {
            
            System.out.println("\n--- Your Reservations ---");
            for (int i = 0; i < myRes.size(); i++) {
                System.out.println((i + 1) + ". " + myRes.get(i).toString());
            }

            
            System.out.print("\nEnter number to CANCEL (or 0 to go back): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice > 0 && choice <= myRes.size()) {
                Reservation toCancel = myRes.get(choice - 1);
                
                
                toCancel.cancel();
                
                
                HotelDatabase.reservations.remove(toCancel);
            }
        }
    }
}
             else if (act == 3) { 
                System.out.print("Enter amount to add: ");
                double amount = scanner.nextDouble(); scanner.nextLine();
                currentGuest.topUpBalance(amount);
            } 
            else if (act == 4) { 
               currentGuest.requestExtraAmenities(scanner);
            }
    }
}


    private static void handleAdmin(String user, String pass, Scanner scanner) {
        Admin currentAdmin = null;
        for (Admin a : HotelDatabase.admins) {
            if (a.login(user, pass)) { currentAdmin = a; break; }
        }
        if (currentAdmin == null) return;

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Create Room Type\n2. Create Room (With Price & View)\n3. View All Data (Includes Reservations)\n4.Update room Price \n5.Delete Room \n6.Update RoomType \n7.assigen amenities to rooms\n8.create amenity \n9.update amenity\n10.Delete amenity \n0. Logout");
            int act = scanner.nextInt(); scanner.nextLine();
            if (act == 0) return;

            if (act == 1) {
                System.out.print("New Room Type Name (e.g., Suite, Single): ");
                
                HotelDatabase.roomTypes.add(currentAdmin.createRoomType(scanner.nextLine()));
            }else if (act == 2) {
            currentAdmin.createRoom(scanner);

            } else if (act == 3) {
               currentAdmin.viewAllData();
            }
            else if (act==4){
                if(HotelDatabase.rooms.isEmpty())
                {
                    System.out.println("there is no rooms to update");
                }
                else{
                System.out.print("Enter Room Number to update: ");
        String rNum = scanner.nextLine();
        System.out.print("Enter New Price: ");
        double nPrice = scanner.nextDouble(); scanner.nextLine();
    
        currentAdmin.updateRoomPrice(rNum, nPrice);
            }
        }
            else if (act==5){
                if(HotelDatabase.rooms.isEmpty())
                {
                    System.out.println("there is no rooms to delete");
                }
                else{
                System.out.print("Enter Room Number to DELETE: ");
                String rNum = scanner.nextLine();
                System.out.println("Are you sure? (y/n)");
                if(scanner.nextLine().equalsIgnoreCase("y")) {
                currentAdmin.deleteRoom(rNum);
                }
            }
        }
            else if (act==6){
                currentAdmin.handleRoomTypeUpdate(scanner);
            }
            else if (act == 7) { 
                currentAdmin.linkAmenityToRoom(scanner); 
                }
                else if (act == 8) { // Create
                    currentAdmin.createAmenity(scanner);
        }
        else if (act == 9) { // Update
            currentAdmin.updateAmenity(scanner);
        }
        else if (act == 10) { // Delete
                currentAdmin.deleteAmenity(scanner);
            }
        }
    }

    private static void handleReceptionist(String user, String pass, Scanner scanner) {
        Receptionist currentRec = null;
        for (Receptionist r : HotelDatabase.receptionists) {
            if (r.login(user, pass)) { currentRec = r; break; }
        }
        if (currentRec == null) return;

        while (true) {
            System.out.println("\n--- Receptionist Menu ---");
            System.out.println("1. Check-In Guest (Confirm Reservation)\n2. View All Reservations\n3.CheckOut\n4. Logout");
            int act = scanner.nextInt(); scanner.nextLine();
            if (act == 4) break;

            if (act == 1) {
                System.out.print("Enter Reservation ID to Check-In: ");
                String id = scanner.nextLine();
                for (Reservation res : HotelDatabase.reservations) {
                    if (res.getReservationId().equals(id)) {
                        res.confirm(); 
                        break;
                    }
                }
            } else if (act == 2) {
                System.out.println("\n--- ALL RESERVATIONS IN SYSTEM ---");
                for (Reservation res : HotelDatabase.reservations) {
                    System.out.println(res);
                }
            
            }
            else if(act == 3){
                System.out.print("Enter Reservation ID to Check-Out: ");
            String id = scanner.nextLine();
            
            Reservation foundRes = null;
            for (Reservation res : HotelDatabase.reservations) {
                if (res.getReservationId().equals(id)) {
                    foundRes = res;
                    break;
                }
                
                }
                if(foundRes!=null)
                {
                    currentRec.checkOut(foundRes);
                    HotelDatabase.reservations.remove(foundRes);
                    System.out.println("Guest has successfully departed.");
                    } 
                    else {
                    System.out.println("Error: Reservation ID not found.");
                 }
                }

            }
        }
    }