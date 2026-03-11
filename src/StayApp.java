import java.util.*;

/**
 * Book My Stay App
 * Version: 11.0
 * Description:
 * Simulates concurrent booking requests using threads
 * and ensures thread-safe room allocation.
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
}

// -------------------- SHARED BOOKING SYSTEM --------------------

class BookingSystem {

    private Queue<Reservation> bookingQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();

    public BookingSystem() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // synchronized request addition
    public synchronized void addRequest(Reservation r) {
        bookingQueue.offer(r);
        System.out.println(Thread.currentThread().getName() +
                " added booking request for " + r.getGuestName());
    }

    // synchronized booking processing (critical section)
    public synchronized void processRequest() {

        if (bookingQueue.isEmpty()) {
            return;
        }

        Reservation r = bookingQueue.poll();

        String roomType = r.getRoomType();
        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            System.out.println(Thread.currentThread().getName()
                    + " confirmed booking -> "
                    + r.getGuestName()
                    + " | " + roomType);

        } else {

            System.out.println(Thread.currentThread().getName()
                    + " booking failed -> "
                    + r.getGuestName()
                    + " | No rooms available");
        }
    }

    public void displayInventory() {

        System.out.println("\nFinal Inventory Status:");

        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// -------------------- BOOKING THREAD --------------------

class BookingProcessor extends Thread {

    private BookingSystem system;

    public BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    public void run() {

        for (int i = 0; i < 2; i++) {
            system.processRequest();

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 11.0");
        System.out.println("Concurrent Booking Simulation");
        System.out.println("===============================================");

        BookingSystem system = new BookingSystem();

        // Simulated guest booking requests
        system.addRequest(new Reservation("Alice", "Single Room"));
        system.addRequest(new Reservation("Bob", "Single Room"));
        system.addRequest(new Reservation("Charlie", "Suite Room"));
        system.addRequest(new Reservation("Diana", "Suite Room"));

        // multiple threads processing bookings
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
        }

        system.displayInventory();

        System.out.println("===============================================");
    }
}