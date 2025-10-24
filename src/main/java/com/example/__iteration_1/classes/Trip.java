package com.example.__iteration_1.classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Trip {
    private String tripId;
    private List<Reservation> reservations;
    private LocalDate bookingDate;
    private Connection connection;

    public Trip(Connection connection) {
        this.tripId = generateTripId();
        this.reservations = new ArrayList<>();
        this.bookingDate = LocalDate.now();
        this.connection = connection;
    }

    private String generateTripId() {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "TRP-" + uuid.substring(0, 4) + "-" + uuid.substring(4, 8);
    }

    public void addReservation(Reservation reservation) {
        for (Reservation r : reservations) {
            if (r.getClient().equals(reservation.getClient())) {
                throw new IllegalArgumentException(
                        "Client " + reservation.getClient().getLastName() +
                                " already has a reservation for this connection"
                );
            }
        }
        reservations.add(reservation);
    }

    public boolean isCurrent() {
        return bookingDate.equals(LocalDate.now()) || bookingDate.isAfter(LocalDate.now());
    }

    public String getTripId() {
        return tripId;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public Connection getConnection() {
        return connection;
    }

    public double getTotalPrice() {
        double total = 0;
        for (Reservation r : reservations) {
            total += r.getTicket().getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Trip ID: ").append(tripId).append(" ===\n");
        sb.append("Booking Date: ").append(bookingDate).append("\n");
        sb.append("Connection: ").append(connection.getDepartureCity())
                .append(" -> ").append(connection.getArrivalCity()).append("\n");
        sb.append("Departure: ").append(connection.getTimetable().getDepartureTime())
                .append(" | Arrival: ").append(connection.getTimetable().getArrivalTime()).append("\n");
        sb.append("Number of Travelers: ").append(reservations.size()).append("\n");
        sb.append("Reservations:\n");
        for (Reservation r : reservations) {
            sb.append("  - ").append(r.getClient().getFirstName())
                    .append(" ").append(r.getClient().getLastName())
                    .append(" (Age: ").append(r.getClient().getAge()).append(")")
                    .append(" | Ticket #").append(r.getTicket().getTicketId())
                    .append(" | ").append(r.isFirstClass() ? "First" : "Second")
                    .append(" Class | €").append(String.format("%.2f", r.getTicket().getPrice()))
                    .append("\n");
        }
        sb.append("Total Price: €").append(String.format("%.2f", getTotalPrice())).append("\n");
        return sb.toString();
    }
}
