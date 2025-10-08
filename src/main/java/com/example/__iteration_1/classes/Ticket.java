package com.example.__iteration_1.classes;

public abstract class Ticket {

    private double price;

    public Ticket() {}

    public  abstract double getPrice();

    public abstract void setPrice(double price);

    @Override
    public String toString() {
        return "Ticket{" +
                " price in euro =" + price +
                '}';
    }
}
