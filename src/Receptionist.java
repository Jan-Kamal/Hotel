import java.time.LocalDate;


public class Receptionist extends Staff {

    
    public Receptionist(String username, String password,
                        LocalDate dateOfBirth, int workingHours) {
       
        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
    }

    @Override
    public void performDuties() {
        System.out.println("Receptionist " + getUsername() + " is managing check-ins and check-outs.");
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
    
    System.out.println("Check-out complete for ID: " + reservation.getReservationId());
    }
}