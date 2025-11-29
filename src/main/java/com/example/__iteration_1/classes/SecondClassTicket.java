package com.example.__iteration_1.classes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SECOND_CLASS_TICKET")
public class SecondClassTicket extends Ticket {

    public SecondClassTicket(double price) {
        super(price);
    }

    public SecondClassTicket() {
        super();
    }

    @Override
    public String toString() {
        return "SecondClassTicket{" +
                "ticketId=" + getTicketId() +
                ", price in euro=" + getPrice() +
                '}';
    }
}