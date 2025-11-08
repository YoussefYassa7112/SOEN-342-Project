package com.example.__iteration_1.classes;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Reservation {
    @Id
    @Column(name = "reservation_id")
    private String reservationId = UUID.randomUUID().toString();;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "connection_route_id")
    private String connectionRouteId;

    @Transient
    private Connection connection;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "reservation_name")
    private String reservationName;

    @Column(name = "is_first_class")
    private boolean isFirstClass;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    public Reservation(Client client, Connection connection, boolean isFirstClass) {
        this.client = client;
        this.connection = connection;
        this.connectionRouteId = connection.getRouteId();
        this.isFirstClass = isFirstClass;

        if (isFirstClass) {
            this.ticket = new FirstClassTicket(connection.getFirstClassTicket().getPrice());
        } else {
            this.ticket = new SecondClassTicket(connection.getSecondClassTicket().getPrice());
        }
    }

    public Reservation() {

    }

    public Client getClient() {
        return client;
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

    public Ticket getTicket() {
        return ticket;
    }

    public boolean isFirstClass() {
        return isFirstClass;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "client=" + client +
                ", connection=" + connection +
                ", ticket=" + ticket +
                ", reservationName='" + reservationName + '\'' +
                ", isFirstClass=" + isFirstClass +
                '}';
    }
}
