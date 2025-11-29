package com.example.__iteration_1.classes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FIRST_CLASS_TICKET")
public class FirstClassTicket extends Ticket {

    public FirstClassTicket(double price) {
        super(price);
    }

    public FirstClassTicket() {
        super();
    }

    @Override
    public String toString() {
        return "FirstClassTicket{" +
                "ticketId=" + getTicketId() +
                ", price in euro=" + getPrice() +
                '}';
    }
}