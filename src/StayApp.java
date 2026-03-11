import java.io.*;
import java.util.*;

/**
 * Book My Stay App
 * Version: 12.0
 * Description:
 * Demonstrates data persistence using serialization
 * and system recovery using deserialization.
 */

// -------------------- RESERVATION MODEL --------------------

class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// -------------------- SYSTEM STATE --------------------

class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// -------------------- PERSISTENCE SERVICE --------------------

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // save system state
    public void save(SystemState state) {

        try {

            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(FILE_NAME));

            out.writeObject(state);

            out.close();

            System.out.println("System state saved successfully.");

        } catch (Exception e) {

            System.out.println("Error saving system state.");
        }
    }

    // load system state
    public SystemState load() {

        try {

            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(FILE_NAME));

            SystemState state = (SystemState) in.readObject();

            in.close();

            System.out.println("System state restored from file.");

            return state;

        } catch (Exception e) {

            System.out.println("No saved state found. Starting fresh.");

            return null;
        }
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 12.0");
        System.out.println("Data Persistence & System Recovery");
        System.out.println("===============================================");

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load();

        List<Reservation> bookingHistory;
        Map<String, Integer> inventory;

        if (state == null) {

            bookingHistory = new ArrayList<>();
            inventory = new HashMap<>();

            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);

        } else {

            bookingHistory = state.bookingHistory;
            inventory = state.inventory;
        }

        // simulate new booking
        Reservation r1 = new Reservation("R201", "Alice", "Single Room");

        bookingHistory.add(r1);
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        System.out.println("\nNew Booking Added:");
        System.out.println(r1);

        // display current data
        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        System.out.println("\nCurrent Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }

        // save system state
        persistence.save(new SystemState(bookingHistory, inventory));

        System.out.println("===============================================");
    }
}