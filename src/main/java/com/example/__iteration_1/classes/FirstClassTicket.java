package com.example.__iteration_1.classes;

public class FirstClassTicket extends Ticket {

    private double price;

    public FirstClassTicket(double price) {
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
        return "FirstClassTicket{" +
                " price in euro =" + price +
                '}';
    }



}
