package com.example.__iteration_1.classes;

public class SecondClassTicket extends Ticket {

    private double price;

    public SecondClassTicket(double price) {
        super(price);
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SecondClassTicket{" +
                "ticketId=" + getTicketId() +
                ", price in euro=" + price +
                '}';
    }
}