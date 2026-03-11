import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay App
 * Version: 5.0
 * Description:
 * Introduces booking request intake using FIFO Queue.
 * Ensures fair first-come-first-served ordering.
 *
 * @author BookMyStay Team
 * @version 5.0
 */

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

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Requested Room: " + roomType;
    }
}

// -------------------- BOOKING REQUEST QUEUE --------------------

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request (FIFO)
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayQueue() {
        System.out.println("\n--- Current Booking Request Queue (FIFO Order) ---\n");
        for (Reservation reservation : requestQueue) {
            System.out.println(reservation);
        }
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 5.0");
        System.out.println("===============================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulated guest booking requests (arrival order)
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Suite Room");
        Reservation r3 = new Reservation("Charlie", "Single Room");
        Reservation r4 = new Reservation("Diana", "Double Room");

        // Add to queue (FIFO preserved)
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.addRequest(r4);

        // Display queue (no allocation yet)
        bookingQueue.displayQueue();

        System.out.println("\nAll requests stored in arrival order.");
        System.out.println("No inventory mutation has occurred.");
        System.out.println("Requests are ready for allocation processing.");
        System.out.println("===============================================");
    }
}