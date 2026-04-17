import java.time.LocalDate;
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
            if(choice==2) HotelDatabase.registerNewGuest(scanner);
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
               if (act == 1) { 
    System.out.print("Preferred Room Type (e.g., Suite, Single): ");
    String typePref = scanner.nextLine();
    System.out.print("Preferred View (e.g., Sea View, Pool View): ");
    String viewPref = scanner.nextLine();

    boolean autoReserved = false;
    
    // --- 1. AUTO-RESERVE SECTION ---
    for (Room r : HotelDatabase.rooms) {
        if (r.isAvailable() && 
            r.getRoomType().getTypeName().equalsIgnoreCase(typePref) && 
            r.getViewPreference().equalsIgnoreCase(viewPref)) {
            
            System.out.println("\n>>> PERFECT MATCH FOUND! <<<");
            if (currentGuest.canAfford(r.getPrice())) {
                
                // ASKING FOR PAYMENT METHOD
                System.out.println("Select Payment Method: 1.CASH 2.CREDIT_CARD 3.ONLINE");
                int pChoice = scanner.nextInt(); scanner.nextLine();
                Invoice.PaymentMethod m = (pChoice == 1) ? Invoice.PaymentMethod.CASH : 
                                         (pChoice == 2) ? Invoice.PaymentMethod.CREDIT_CARD : 
                                         Invoice.PaymentMethod.ONLINE;

                // DEDUCTING FROM SHARED BALANCE
                currentGuest.deductBalance(r.getPrice());

                // CREATING INVOICE AND RESERVATION
                Invoice inv = new Invoice(r.getPrice(), m);
                Reservation res = new Reservation(currentGuest, r, LocalDate.now(), LocalDate.now().plusDays(2));
                HotelDatabase.reservations.add(res);
                
                System.out.println("AUTO-RESERVED! New Balance: $" + currentGuest.getBalance());
                inv.printReceipt(); // PRINT THE RECEIPT
                
                autoReserved = true;
            } else {
                System.out.println("Match found, but insufficient funds to auto-reserve.");
                continue;
            }
            break;
        }
    }

    // --- 2. MANUAL RESERVE SECTION ---
    if (!autoReserved) {
        System.out.println("\nNo exact match could be auto-reserved. Available options:");
        for (Room r : HotelDatabase.rooms) {
            if (r.isAvailable()) System.out.println(r);
        }
        System.out.print("Enter Room Number to manually reserve (or 'exit'): ");
        String rNum = scanner.nextLine();
        
        for (Room r : HotelDatabase.rooms) {
            if (r.getRoomNumber().equalsIgnoreCase(rNum) && r.isAvailable()) {
                if (currentGuest.canAfford(r.getPrice())) {
                    
                    // ASKING FOR PAYMENT METHOD
                    System.out.println("Select Payment Method: 1.CASH 2.CREDIT_CARD 3.ONLINE");
                    int pChoice = scanner.nextInt(); scanner.nextLine();
                    Invoice.PaymentMethod m = (pChoice == 1) ? Invoice.PaymentMethod.CASH : 
                                             (pChoice == 2) ? Invoice.PaymentMethod.CREDIT_CARD : 
                                             Invoice.PaymentMethod.ONLINE;

                    // DEDUCTING FROM SHARED BALANCE
                    currentGuest.deductBalance(r.getPrice());

                    // CREATING INVOICE AND RESERVATION
                    Invoice inv = new Invoice(r.getPrice(), m);
                    Reservation res = new Reservation(currentGuest, r, LocalDate.now(), LocalDate.now().plusDays(2));
                    HotelDatabase.reservations.add(res);
                    
                    System.out.println("Manual Reservation success! New Balance: $" + currentGuest.getBalance());
                    inv.printReceipt(); // PRINT THE RECEIPT
                } else {
                    System.out.println("Insufficient funds!");
                }
                break;
            }
        }
    }
}
            } else if (act == 3) { 
                System.out.print("Enter amount to add: ");
                double amount = scanner.nextDouble(); scanner.nextLine();
                currentGuest.topUpBalance(amount);
            } else if (act == 4) { 
                System.out.println("\n--- Request Extra Amenities ---");
                Reservation activeRes = null;
                for (Reservation res : HotelDatabase.reservations) {
                    if (res.getGuest().getUsername().equals(currentGuest.getUsername()) && res.getStatus() != Reservation.ReservationStatus.CANCELLED) {
                        System.out.println(res.getReservationId() + " -> Room " + res.getRoom().getRoomNumber());
                        activeRes = res; 
                        break;
                    }
                }

                if (activeRes == null) {
                    System.out.println("You have no active reservations to upgrade.");
                    continue;
                }

                boolean isSuite = activeRes.getRoom().getRoomType().getTypeName().toLowerCase().contains("suite");
                
                System.out.println("1. Add Extra Bed");
                if (!isSuite) System.out.println("2. Add Unlimited WiFi (+$50)");
                System.out.print("Choice: ");
                int amChoice = scanner.nextInt(); scanner.nextLine();

                if (amChoice == 1) {
                    double bedPrice = isSuite ? 50.0 : 100.0; 
                    if (currentGuest.canAfford(bedPrice)) {
                        currentGuest.deductBalance(bedPrice);
                        activeRes.addExtraAmenity(new Amenity("Extra Bed"));
                        System.out.println("Extra Bed added! Charged $" + bedPrice);
                    } else { System.out.println("Insufficient funds!"); }
                } else if (amChoice == 2 && !isSuite) {
                    if (currentGuest.canAfford(50.0)) {
                        currentGuest.deductBalance(50.0);
                        activeRes.addExtraAmenity(new Amenity("Unlimited WiFi"));
                        System.out.println("Unlimited WiFi added! Charged $50.0");
                    } else { System.out.println("Insufficient funds!"); }
                } else {
                    System.out.println("Invalid choice or already included in Suite.");
                }
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
            System.out.println("1. Create Room Type\n2. Create Room (With Price & View)\n3. View All Data (Includes Reservations)\n4.Update room Price \n5.Delete Room \n6.Update RoomType \n7. Logout");
            int act = scanner.nextInt(); scanner.nextLine();
            if (act == 7) return;

            if (act == 1) {
                System.out.print("New Room Type Name (e.g., Suite, Single): ");
                HotelDatabase.roomTypes.add(currentAdmin.createRoomType(scanner.nextLine()));
            } else if (act == 2) {
                if (HotelDatabase.roomTypes.isEmpty()) {
                    System.out.println("Error: Create a Room Type first!"); continue;
                }

                System.out.print("Room Number: ");
                String num = scanner.nextLine();
                
                boolean exists = false;
                for (Room r : HotelDatabase.rooms) {
                    if (r.getRoomNumber().equalsIgnoreCase(num)) { exists = true; break; }
                }
                if (exists) { System.out.println("Error: Room " + num + " already exists!"); continue; }

                System.out.print("Set Room Price: ");
                double price = scanner.nextDouble(); scanner.nextLine();

                System.out.print("Set Room View (e.g., Sea View, Pool View): ");
                String view = scanner.nextLine();

                System.out.println("\nSelect Room Type:");
                for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
                    System.out.println(i + ". " + HotelDatabase.roomTypes.get(i).getTypeName());
                }
                System.out.print("Choice: ");
                int typeChoice = scanner.nextInt(); scanner.nextLine();

                if (typeChoice >= 0 && typeChoice < HotelDatabase.roomTypes.size()) {
                    RoomType selectedType = HotelDatabase.roomTypes.get(typeChoice);
                    Room newRoom = new Room(num, selectedType, price, view); 
                    HotelDatabase.rooms.add(newRoom);
                    System.out.println("Room " + num + " created!");
                }
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