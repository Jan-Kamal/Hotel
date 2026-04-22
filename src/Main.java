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
   else if (choice == 2) {
    try {
        HotelDatabase.registerNewGuest(scanner);
    } 
    catch (NegativeBalanceException e) {
       
        System.out.println("\n[BALANCE ERROR]: " + e.getMessage());
        System.out.println("Please provide a valid starting balance to register.");
    } 
    catch (IllegalArgumentException e) {
        
        System.out.println("\n[SECURITY ERROR]: " + e.getMessage());
        System.out.println("Please try again with a password at least 6 characters long.");
    }
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
            int act=-1;
                System.out.print("Choice: ");
            try {
            act = scanner.nextInt(); 
            scanner.nextLine(); 
        } catch (java.util.InputMismatchException e) {
            System.out.println("\n[INPUT ERROR]: Please enter a valid number, not text.");
            scanner.nextLine();
            continue;
        }



            if (act == 5) break;

         
               if (act == 1) { 
                currentGuest.ReserveRoom(scanner);
                }
else if (act == 2) { 
    currentGuest.View_CancelReservation(scanner);  
}
             else if (act == 3) { 
               try {
                System.out.print("Enter amount to add: ");
                double amount = scanner.nextDouble(); 
                scanner.nextLine();
                currentGuest.topUpBalance(amount);
            } catch (java.util.InputMismatchException e) {
                System.out.println("\n[INPUT ERROR]: Please enter a valid number for the amount.");
                scanner.nextLine();
            }
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
                int act=-1;
                System.out.print("Choice: ");
            try {
            act = scanner.nextInt();
            scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("\n[INPUT ERROR]: Please enter a valid number, not text.");
                scanner.nextLine();
                continue;
            }
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
        try {
            double nPrice = scanner.nextDouble(); 
            scanner.nextLine();
            
            
            currentAdmin.updateRoomPrice(rNum, nPrice); 
            
        } catch (java.util.InputMismatchException e) {
            System.out.println("\n[INPUT ERROR]: Price must be a valid number (e.g., 150.50).");
            scanner.nextLine();
        }
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
            int act=-1;
            System.out.print("Choice: ");
            try {
                act = scanner.nextInt();
                scanner.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("\n[INPUT ERROR]: Please enter a valid number, not text.");
                scanner.nextLine();
            }
            if (act == 4) break;

              if (act == 1) {
                System.out.print("Enter Reservation ID to Check-In: ");
String id = scanner.nextLine();

boolean found = false;
for (Reservation res : HotelDatabase.reservations) {
    if (res.getReservationId().equalsIgnoreCase(id)) {
        
        currentRec.checkIn(res); 
        found = true;
        break;
    }
}

if (!found) {
    System.out.println("[ERROR]: No reservation found with ID: " + id);
}
            }
                else if (act == 2) { 
                    currentRec.viewAReservations();
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