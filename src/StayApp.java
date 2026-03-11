import java.util.*;

/**
 * Book My Stay App
 * Version: 9.0
 * Description:
 * Demonstrates input validation and custom exception handling
 * to prevent invalid booking states.
 */

// -------------------- CUSTOM EXCEPTION --------------------

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// -------------------- RESERVATION MODEL --------------------

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- INVENTORY SERVICE --------------------

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected: " + roomType);
        }
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }

        inventory.put(roomType, available - 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// -------------------- BOOKING SERVICE --------------------

class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processBooking(Reservation reservation) {

        try {

            inventory.validateRoomType(reservation.getRoomType());

            inventory.allocateRoom(reservation.getRoomType());

            System.out.println("Booking Confirmed -> "
                    + reservation.getGuestName()
                    + " | Room: " + reservation.getRoomType());

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed -> " + e.getMessage());
        }
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 9.0");
        System.out.println("Error Handling & Validation");
        System.out.println("===============================================");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Valid booking
        Reservation r1 = new Reservation("Alice", "Single Room");

        // Invalid room type
        Reservation r2 = new Reservation("Bob", "Luxury Room");

        // Valid booking
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Exceeding inventory
        Reservation r4 = new Reservation("David", "Suite Room");

        bookingService.processBooking(r1);
        bookingService.processBooking(r2);
        bookingService.processBooking(r3);
        bookingService.processBooking(r4);

        inventory.displayInventory();

        System.out.println("===============================================");
    }
}