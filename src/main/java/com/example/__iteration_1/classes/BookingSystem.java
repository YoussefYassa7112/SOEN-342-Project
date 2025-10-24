package com.example.__iteration_1.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingSystem {
    private Map<String, Client> clients; // Key: client ID
    private List<Trip> allTrips;

    public BookingSystem() {
        this.clients = new HashMap<>();
        this.allTrips = new ArrayList<>();
    }

    public Trip bookTrip(List<Client> travelers, Connection connection, boolean[] isFirstClass) {
        if (travelers == null || travelers.isEmpty()) {
            throw new IllegalArgumentException("At least one traveler is required");
        }

        if (isFirstClass.length != travelers.size()) {
            throw new IllegalArgumentException("Class preferences must be specified for each traveler");
        }

        Trip trip = new Trip(connection);

        for (int i = 0; i < travelers.size(); i++) {
            Client client = travelers.get(i);

            if (!clients.containsKey(client.getId())) {
                clients.put(client.getId(), client);
            } else {
                client = clients.get(client.getId());
            }

            Reservation reservation = new Reservation(client, connection, isFirstClass[i]);
            trip.addReservation(reservation);

            client.addTrip(trip);
        }

        allTrips.add(trip);

        return trip;
    }

    public Map<String, List<Trip>> viewTrips(String lastName, String id) {
        Map<String, List<Trip>> result = new HashMap<>();

        Client client = clients.get(id);

        if (client == null || !client.getLastName().equalsIgnoreCase(lastName)) {
            result.put("current", new ArrayList<>());
            result.put("past", new ArrayList<>());
            return result;
        }

        result.put("current", client.getCurrentTrips());
        result.put("past", client.getPastTrips());

        return result;
    }

    public boolean cancelTrip(String tripId) {
        Trip tripToCancel = null;
        for (Trip trip : allTrips) {
            if (trip.getTripId().equals(tripId)) {
                tripToCancel = trip;
                break;
            }
        }

        if (tripToCancel == null) {
            return false;
        }

        for (Reservation reservation : tripToCancel.getReservations()) {
            Client client = reservation.getClient();
            client.getTrips().remove(tripToCancel);
        }

        allTrips.remove(tripToCancel);
        return true;
    }

    public boolean addTravelerToTrip(String tripId, Client newTraveler, boolean isFirstClass) {
        Trip trip = getTripById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found");
        }

        if (!clients.containsKey(newTraveler.getId())) {
            clients.put(newTraveler.getId(), newTraveler);
        } else {
            // Use the existing client object from the system
            newTraveler = clients.get(newTraveler.getId());
        }

        Reservation reservation = new Reservation(newTraveler, trip.getConnection(), isFirstClass);
        trip.addReservation(reservation);

        newTraveler.addTrip(trip);

        return true;
    }

    public Client getClient(String id) {
        return clients.get(id);
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients.values());
    }

    public List<Trip> getAllTrips() {
        return new ArrayList<>(allTrips);
    }

    public Trip getTripById(String tripId) {
        for (Trip trip : allTrips) {
            if (trip.getTripId().equals(tripId)) {
                return trip;
            }
        }
        return null;
    }
}