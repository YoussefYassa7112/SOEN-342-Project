package com.example.__iteration_1.classes;

public abstract class Ticket {
    private static int ticketCounter = 10000;
    private int ticketId;
    private double price;

    public Ticket(double price) {
        this.ticketId = ++ticketCounter;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public abstract double getPrice();

    public abstract void setPrice(double price);

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", price in euro=" + price +
                '}';
    }
}
