import java.util.*;

/**
 * Book My Stay App
 * Version: 8.0
 * Description:
 * Maintains booking history and generates reports.
 */

// -------------------- RESERVATION MODEL --------------------

class Reservation {

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

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType;
    }
}

// -------------------- BOOKING HISTORY --------------------

class BookingHistory {

    private List<Reservation> reservationHistory;

    public BookingHistory() {
        reservationHistory = new ArrayList<>();
    }

    // store confirmed booking
    public void addReservation(Reservation reservation) {
        reservationHistory.add(reservation);
        System.out.println("Reservation stored in history -> " + reservation.getReservationId());
    }

    public List<Reservation> getAllReservations() {
        return reservationHistory;
    }
}

// -------------------- REPORT SERVICE --------------------

class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n---- Booking History Report ----\n");

        for (Reservation r : reservations) {
            System.out.println(r);
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}

// -------------------- APPLICATION ENTRY --------------------

public class StayApp {

    public static void main(String[] args) {

        System.out.println("===============================================");
        System.out.println("Book My Stay - Hotel Booking Management System");
        System.out.println("Version 8.0");
        System.out.println("Booking History & Reporting");
        System.out.println("===============================================");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulated confirmed reservations
        Reservation r1 = new Reservation("R101", "Alice", "Single Room");
        Reservation r2 = new Reservation("R102", "Bob", "Suite Room");
        Reservation r3 = new Reservation("R103", "Charlie", "Double Room");

        // store in history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // admin generates report
        reportService.generateReport(history.getAllReservations());

        System.out.println("\n===============================================");
    }
}