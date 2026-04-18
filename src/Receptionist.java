import java.time.LocalDate;
import java.util.Scanner;


public class Receptionist extends Staff {
    private LocalDate date;

    
    public Receptionist(String username, String password,
                        LocalDate dateOfBirth, int workingHours) {
       
        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
    }

    @Override
    public void performDuties() {
        System.out.println("Receptionist " + getUsername() + " is managing check-ins and check-outs.");
    }
    public static String askForDate(String label, Scanner scan) {
    System.out.print("Please enter " + label + " Date (YYYY-MM-DD): ");
    return scan.nextLine();
}

    public void checkIn(Reservation reservation) {
      
        if (reservation.getStatus() == Reservation.ReservationStatus.PENDING) {
            reservation.confirm(); 
            System.out.println("Receptionist " + getUsername() +
                    " checked in guest for reservation ID: " + reservation.getReservationId());
        } else {
            System.out.println("Cannot check in. Reservation status is currently: " + reservation.getStatus());
        }
    }

public void checkOut(Reservation reservation) {
    System.out.println("Receptionist " + getUsername() +
            " is checking out guest: " + reservation.getGuest().getUsername());
    reservation.getRoom().setAvailable(true);
    System.out.println("date of checkout:" + date);
    System.out.println("Check-out complete for ID: " + reservation.getReservationId());
    
    }
   
}
