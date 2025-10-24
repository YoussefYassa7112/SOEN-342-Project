package com.example.__iteration_1.classes;

public class Reservation {
    private Client client;
    private Connection connection;
    private Ticket ticket;
    private boolean isFirstClass;

    public Reservation(Client client, Connection connection, boolean isFirstClass) {
        this.client = client;
        this.connection = connection;
        this.isFirstClass = isFirstClass;

        if (isFirstClass) {
            this.ticket = new FirstClassTicket(connection.getFirstClassTicket().getPrice());
        } else {
            this.ticket = new SecondClassTicket(connection.getSecondClassTicket().getPrice());
        }
    }

    public Client getClient() {
        return client;
    }

    public Connection getConnection() {
        return connection;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public boolean isFirstClass() {
        return isFirstClass;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "client=" + client.getFirstName() + " " + client.getLastName() +
                ", connection=" + connection.getDepartureCity() + " -> " + connection.getArrivalCity() +
                ", ticket=" + ticket +
                ", class=" + (isFirstClass ? "First" : "Second") +
                '}';
    }
}
