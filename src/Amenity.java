
import java.util.Scanner;

public class Amenity {
    private String name;

    public Amenity(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name;
    }
    
    public static void createAmenity(Scanner sc) {
        System.out.print("Enter New Amenity Name: ");
        String name = sc.nextLine();
        
        
        Amenity newAmenity = new Amenity(name);
        
        
        HotelDatabase.amenitys.add(newAmenity);
        
        System.out.println("Success: '" + name + "' added to the system.");
    }
}