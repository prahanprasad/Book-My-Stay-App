/**
 * Book My Stay App
 * Version: 2.1
 * Description:
 * Demonstrates basic object modeling using abstraction,
 * inheritance, polymorphism, and static availability.
 *
 * @author BookMyStay Team
 * @version 2.1
 */

abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Beds: " + numberOfBeds);
        System.out.println("Price Per Night: $" + pricePerNight);
    }
}

// Concrete Room Types

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 100.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 180.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 300.0);
    }
}

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 2.1");
        System.out.println("===============================================");

        // Polymorphism: Referencing using Room type
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability Variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display Room Information
        System.out.println("\n--- Available Room Types ---\n");

        single.displayRoomDetails();
        System.out.println("Available Units: " + singleAvailability);
        System.out.println("--------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available Units: " + doubleAvailability);
        System.out.println("--------------------------------");

        suite.displayRoomDetails();
        System.out.println("Available Units: " + suiteAvailability);
        System.out.println("--------------------------------");

        System.out.println("Application terminated successfully.");
        System.out.println("===============================================");
    }
}