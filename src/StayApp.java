import java.util.*;


/**
 * Book My Stay App
 * Version: 6.0
 * Description:
 * Processes booking requests and allocates rooms safely.
 * Prevents double booking using Set and maintains inventory.
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



// -------------------- INVENTORY SERVICE --------------------

class InventoryService {

    private Map<String, Integer> roomInventory = new HashMap<>();

    public InventoryService() {
        roomInventory.put("Single Room", 2);
        roomInventory.put("Double Room", 2);
        roomInventory.put("Suite Room", 1);
    }

    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    public void decreaseInventory(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory Status:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type + " : " + roomInventory.get(type));
        }
    }
}



// -------------------- BOOKING SERVICE --------------------

class BookingService {

    private Queue<Reservation> requestQueue = new LinkedList<>();

    private Set<String> allocatedRoomIds = new HashSet<>();

    private Map<String, Set<String>> roomTypeMap = new HashMap<>();

    private InventoryService inventoryService;

    private int roomCounter = 1;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    public void processBookings() {

        System.out.println("\n--- Processing Booking Requests ---\n");

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            if (inventoryService.isAvailable(roomType)) {

                String roomId;

                do {
                    roomId = roomType.substring(0,1).toUpperCase() + roomCounter++;
                } while (allocatedRoomIds.contains(roomId));

                allocatedRoomIds.add(roomId);

                roomTypeMap.putIfAbsent(roomType, new HashSet<>());
                roomTypeMap.get(roomType).add(roomId);

                inventoryService.decreaseInventory(roomType);

                System.out.println("Reservation Confirmed -> "
                        + reservation.getGuestName()
                        + " | Room Type: " + roomType
                        + " | Room ID: " + roomId);

            } else {

                System.out.println("Reservation Failed -> "
                        + reservation.getGuestName()
                        + " | No available rooms for " + roomType);
            }
        }
    }
}



// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 6.0");
        System.out.println("Reservation Confirmation & Room Allocation");
        System.out.println("===============================================");

        InventoryService inventory = new InventoryService();

        BookingService bookingService = new BookingService(inventory);

        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Suite Room");
        Reservation r3 = new Reservation("Charlie", "Single Room");
        Reservation r4 = new Reservation("Diana", "Double Room");

        bookingService.addRequest(r1);
        bookingService.addRequest(r2);
        bookingService.addRequest(r3);
        bookingService.addRequest(r4);

        bookingService.processBookings();

        inventory.displayInventory();

        System.out.println("\n===============================================");
    }
}