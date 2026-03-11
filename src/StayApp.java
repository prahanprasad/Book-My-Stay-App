import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App
 * Version: 4.0
 * Description:
 * Demonstrates room search with read-only access to inventory.
 * Ensures system state remains unchanged during search.
 *
 * @author BookMyStay Team
 * @version 4.0
 */

// -------------------- DOMAIN MODEL --------------------

abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Price Per Night: $" + pricePerNight);
    }
}

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

// -------------------- INVENTORY --------------------

class RoomInventory {

    private Map<String, Integer> availabilityMap;

    public RoomInventory() {
        availabilityMap = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        availabilityMap.put(roomType, count);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }
}

// -------------------- SEARCH SERVICE --------------------

class SearchService {

    public void searchAvailableRooms(Room[] rooms, RoomInventory inventory) {

        System.out.println("\n--- Available Rooms ---\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Defensive check: only show rooms with availability > 0
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available Units: " + available);
                System.out.println("--------------------------------");
            }
        }
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 4.0");
        System.out.println("===============================================");

        // Initialize Rooms
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        Room[] rooms = { single, doubleRoom, suite };

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // Unavailable
        inventory.addRoomType("Suite Room", 2);

        // Perform Search (Read-Only Operation)
        SearchService searchService = new SearchService();
        searchService.searchAvailableRooms(rooms, inventory);

        System.out.println("\nSearch completed successfully.");
        System.out.println("Inventory state remains unchanged.");
        System.out.println("===============================================");
    }
}