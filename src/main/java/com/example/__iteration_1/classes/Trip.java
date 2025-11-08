package com.example.__iteration_1.classes;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "connection_route_id")
    private String connectionRouteId;

    @Transient
    private Connection connection;

    public Trip(Connection connection) {
        this.reservations = new ArrayList<>();
        this.bookingDate = LocalDate.now();
        this.connection = connection;
        this.connectionRouteId = connection.getRouteId();
    }

    public Trip() {

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
        reservation.setTrip(this);
    }

    public boolean isCurrent() {
        return bookingDate.equals(LocalDate.now()) || bookingDate.isAfter(LocalDate.now());
    }

    public Long getTripId() {
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

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionRouteId() {
        return connectionRouteId;
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
