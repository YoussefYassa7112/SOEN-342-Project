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
        sb.append("\n");
        sb.append("  ┌────────────────────────────────────────────────────────┐\n");
        sb.append("  │ TRIP #").append(String.format("%-49d", tripId)).append("│\n");
        sb.append("  ├────────────────────────────────────────────────────────┤\n");
        sb.append("  │ Route     : ").append(String.format("%-43s", connection.getDepartureCity() + " → " + connection.getArrivalCity())).append("│\n");
        sb.append("  │ Schedule  : ").append(String.format("%-43s", connection.getTimetable().getDepartureTime() + " - " + connection.getTimetable().getArrivalTime())).append("│\n");
        sb.append("  │ Booked On : ").append(String.format("%-43s", bookingDate)).append("│\n");
        sb.append("  │ Travelers : ").append(String.format("%-43d", reservations.size())).append("│\n");
        sb.append("  ├────────────────────────────────────────────────────────┤\n");
        sb.append("  │ PASSENGERS                                             │\n");
        sb.append("  ├────────────────────────────────────────────────────────┤\n");
        for (Reservation r : reservations) {
            String passengerInfo = r.getClient().getFirstName() + " " + r.getClient().getLastName() + 
                    " (Age " + r.getClient().getAge() + ")";
            String ticketInfo = (r.isFirstClass() ? "1st" : "2nd") + " Class - €" + 
                    String.format("%.2f", r.getTicket().getPrice());
            sb.append("  │ ").append(String.format("%-30s", passengerInfo)).append(String.format("%-25s", ticketInfo)).append("│\n");
        }
        sb.append("  ├────────────────────────────────────────────────────────┤\n");
        sb.append("  │ TOTAL: €").append(String.format("%-47.2f", getTotalPrice())).append("│\n");
        sb.append("  └────────────────────────────────────────────────────────┘");
        return sb.toString();
    }
}
