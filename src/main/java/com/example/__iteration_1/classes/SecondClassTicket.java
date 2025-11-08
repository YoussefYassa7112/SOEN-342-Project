package com.example.__iteration_1.classes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SECOND_CLASS_TICKET")
public class SecondClassTicket extends Ticket {


    private double price;

    public SecondClassTicket(double price) {
        super(price);
        this.price = price;
    }

    public SecondClassTicket() {
        super();
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