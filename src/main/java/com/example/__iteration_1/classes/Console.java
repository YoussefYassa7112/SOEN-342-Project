package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Console {

    private final String csv = "src/main/resources/eu_rail_network.csv";
    private List<Connection> resultsList = new ArrayList<>();
    private ConnectionCatalog catalog;
    private BookingSystem bookingSystem;

    public Console() {
        this.catalog = new ConnectionCatalog();
        this.bookingSystem = new BookingSystem();
    }

    public void readFile() {
        catalog.readFile(csv);
        resultsList = catalog.getResults();
    }

    public void oneStop() {
        catalog.transitive();
    }

    public void twoStops() {
        catalog.transitiveTwoStops();
    }

    public List<Connection> sortResultsByTripDuration() {
        resultsList = catalog.sortResultsByTripDuration();
        return resultsList;
    }

    public List<Connection> sortResultsByPrice() {
        resultsList = catalog.sortResultsByPrice();
        return resultsList;
    }

    public void printResults() {
        System.out.printf(
                "| %-5s | %-30s | %-30s | %-30s | %-40s | %-40s | %-40s | %-40s |\n",
                "Index", "Departure", "Arrival", "Timetable", "Train", "Days", "1st Class", "2nd Class"
        );
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < resultsList.size(); i++) {
            System.out.printf("| %-5d %s\n", i, resultsList.get(i));
        }
    }

    public void searchConnection() {
        catalog.startNewSearch();
        resultsList = catalog.getResults();
        printResults();
    }

    public void searchConnection(String parameter, String value) {
        switch (parameter) {
            case "departureCity":
                resultsList = catalog.getConnectionsByDepartureCity(value);
                break;
            case "arrivalCity":
                resultsList = catalog.getConnectionsByArrivalCity(value);
                break;
            case "dayOfOperation":
                resultsList = catalog.getConnectionsByDayOfOperation(DaysOfOperation.valueOf(value.toUpperCase()));
                break;
            case "departureTime":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime time = LocalTime.parse(value, formatter);
                resultsList = catalog.getConnectionsByDepartureTime(time);
                break;
            case "arrivalTime":
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime time2 = LocalTime.parse(value, formatter2);
                resultsList = catalog.getConnectionsByArrivalTimeBefore(time2);
                break;
            default:
                System.out.println("Invalid parameter");
        }
    }

    public void resetSearch() {
        resultsList = catalog.resetFilters();
    }

    public Trip bookTrip(Scanner scanner) {
        if (resultsList.isEmpty()) {
            System.out.println("No connections available. Please search for connections first.");
            return null;
        }

        printResults();

        System.out.print("\nEnter the index of the connection you want to book: ");
        int connectionIndex;
        try {
            connectionIndex = Integer.parseInt(scanner.nextLine());
            if (connectionIndex < 0 || connectionIndex >= resultsList.size()) {
                System.out.println("Invalid connection index.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return null;
        }

        Connection selectedConnection = resultsList.get(connectionIndex);

        System.out.print("How many people are traveling? ");
        int numTravelers;
        try {
            numTravelers = Integer.parseInt(scanner.nextLine());
            if (numTravelers <= 0) {
                System.out.println("Number of travelers must be at least 1.");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return null;
        }

        List<Client> travelers = new ArrayList<>();
        boolean[] classPreferences = new boolean[numTravelers];

        for (int i = 0; i < numTravelers; i++) {
            System.out.println("\n--- Traveler " + (i + 1) + " ---");
            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();
            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();
            System.out.print("Age: ");
            int age;
            try {
                age = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Using default age 30.");
                age = 30;
            }
            System.out.print("ID (Passport/State ID): ");
            String id = scanner.nextLine().trim();
            System.out.print("First Class? (yes/no): ");
            String classChoice = scanner.nextLine().trim().toLowerCase();

            Client client = new Client(firstName, lastName, age, id);
            travelers.add(client);
            classPreferences[i] = classChoice.equals("yes") || classChoice.equals("y");
        }

        try {
            Trip trip = bookingSystem.bookTrip(travelers, selectedConnection, classPreferences);
            System.out.println("\n✓ Trip booked successfully!");
            System.out.println(trip);
            return trip;
        } catch (IllegalArgumentException e) {
            System.out.println("Booking failed: " + e.getMessage());
            return null;
        }
    }

    public void viewTrips(Scanner scanner) {
        System.out.print("Enter your Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter your ID: ");
        String id = scanner.nextLine().trim();

        Map<String, List<Trip>> trips = bookingSystem.viewTrips(lastName, id);

        List<Trip> currentTrips = trips.get("current");
        List<Trip> pastTrips = trips.get("past");

        if (currentTrips.isEmpty() && pastTrips.isEmpty()) {
            System.out.println("No trips found for " + lastName + " with ID " + id);
            return;
        }

        System.out.println("\n========== CURRENT/UPCOMING TRIPS ==========");
        if (currentTrips.isEmpty()) {
            System.out.println("No current or upcoming trips.");
        } else {
            for (Trip trip : currentTrips) {
                System.out.println(trip);
            }
        }

        System.out.println("\n========== PAST TRIPS (HISTORY) ==========");
        if (pastTrips.isEmpty()) {
            System.out.println("No past trips.");
        } else {
            for (Trip trip : pastTrips) {
                System.out.println(trip);
            }
        }
    }

    public List<Connection> getResultsList() {
        return resultsList;
    }

    public ConnectionCatalog getCatalog() {
        return catalog;
    }

    public BookingSystem getBookingSystem() {
        return bookingSystem;
    }

    public void showResults() {
        for (Connection connection : resultsList) {
            System.out.println(connection);
        }
    }
}