import java.util.*;

/**
 * Book My Stay App
 * Version: 10.0
 * Description:
 * Supports booking cancellation and inventory rollback.
 */

// -------------------- RESERVATION MODEL --------------------

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getGuestName() {
        return guestName;
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

    public void decrease(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increase(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// -------------------- CANCELLATION SERVICE --------------------

class CancellationService {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Stack<String> releasedRoomIds = new Stack<>();
    private InventoryService inventory;

    public CancellationService(InventoryService inventory) {
        this.inventory = inventory;
    }

    // simulate confirmed booking
    public void confirmBooking(Reservation r) {

        confirmedBookings.put(r.getReservationId(), r);
        inventory.decrease(r.getRoomType());

        System.out.println("Booking Confirmed -> "
                + r.getReservationId() + " | "
                + r.getGuestName() + " | Room ID: " + r.getRoomId());
    }

    // cancel booking
    public void cancelBooking(String reservationId) {

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed -> Reservation not found: " + reservationId);
            return;
        }

        Reservation r = confirmedBookings.remove(reservationId);

        releasedRoomIds.push(r.getRoomId());

        inventory.increase(r.getRoomType());

        System.out.println("Booking Cancelled -> "
                + reservationId + " | Room Released: " + r.getRoomId());
    }

    public void displayRollbackStack() {

        System.out.println("\nReleased Room IDs (Rollback Stack):");

        for (String id : releasedRoomIds) {
            System.out.println(id);
        }
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 10.0");
        System.out.println("Booking Cancellation & Inventory Rollback");
        System.out.println("===============================================");

        InventoryService inventory = new InventoryService();
        CancellationService cancellationService = new CancellationService(inventory);

        Reservation r1 = new Reservation("R101", "Alice", "Single Room", "S1");
        Reservation r2 = new Reservation("R102", "Bob", "Suite Room", "SU1");

        cancellationService.confirmBooking(r1);
        cancellationService.confirmBooking(r2);

        inventory.displayInventory();

        // cancellation requests
        cancellationService.cancelBooking("R102");
        cancellationService.cancelBooking("R200"); // invalid

        inventory.displayInventory();

        cancellationService.displayRollbackStack();

        System.out.println("===============================================");
    }
}