package com.example.__iteration_1.classes;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String firstName;
    private String lastName;
    private int age;
    private String id; // generic ID (passport/state-id)
    private List<Trip> trips;

    public Client(String firstName, String lastName, int age, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.id = id;
        this.trips = new ArrayList<>();
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    public List<Trip> getCurrentTrips() {
        List<Trip> current = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.isCurrent()) {
                current.add(trip);
            }
        }
        return current;
    }

    public List<Trip> getPastTrips() {
        List<Trip> past = new ArrayList<>();
        for (Trip trip : trips) {
            if (!trip.isCurrent()) {
                past.add(trip);
            }
        }
        return past;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id.equals(client.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
