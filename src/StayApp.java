import java.util.*;

/**
 * Book My Stay App
 * Version: 7.0
 * Description:
 * Adds optional services to existing reservations.
 * Demonstrates extensibility using Map and List.
 */

// -------------------- ADD-ON SERVICE MODEL --------------------

class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}

// -------------------- ADD-ON SERVICE MANAGER --------------------

class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // attach service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Service added -> " + service.getServiceName() +
                " for Reservation ID: " + reservationId);
    }

    // display services
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId);

        for (AddOnService service : services) {
            System.out.println(service);
        }
    }

    // calculate cost
    public void calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService service : services) {
                total += service.getPrice();
            }
        }

        System.out.println("Total Add-On Cost: $" + total);
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 7.0");
        System.out.println("Add-On Service Selection");
        System.out.println("===============================================");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Existing reservation IDs from previous use case
        String reservation1 = "R101";
        String reservation2 = "R102";

        // Available services
        AddOnService breakfast = new AddOnService("Breakfast", 15);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 40);
        AddOnService spa = new AddOnService("Spa Access", 60);

        // Guest selects services
        serviceManager.addService(reservation1, breakfast);
        serviceManager.addService(reservation1, spa);

        serviceManager.addService(reservation2, airportPickup);

        // Display services
        serviceManager.displayServices(reservation1);
        serviceManager.calculateTotalCost(reservation1);

        serviceManager.displayServices(reservation2);
        serviceManager.calculateTotalCost(reservation2);

        System.out.println("===============================================");
    }
}