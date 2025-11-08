package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;
import com.example.__iteration_1.repository.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Console {

    private final String csv = "src/main/resources/eu_rail_network.csv";
    private List<Connection> resultsList = new ArrayList<>();
    private ConnectionCatalog catalog;
    private TripRepository tripRepository;
    private ClientRepository clientRepository;
    private ReservationRepository reservationRepository;
    private TicketRepository ticketRepository;

    public Console(TripRepository tripRepository, ClientRepository clientRepository, 
                   ReservationRepository reservationRepository, TicketRepository ticketRepository) {
        this.catalog = new ConnectionCatalog();
        this.tripRepository = tripRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.ticketRepository = ticketRepository;
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
            Trip trip = new Trip(selectedConnection);

            for (int i = 0; i < travelers.size(); i++) {
                Client client = travelers.get(i);

                Optional<Client> existingClient = clientRepository.findById(client.getId());
                if (existingClient.isPresent()) {
                    client = existingClient.get();
                } else {
                    client = clientRepository.save(client);
                }

                Reservation reservation = new Reservation(client, selectedConnection, classPreferences[i]);

                for (Reservation r : trip.getReservations()) {
                    if (r.getClient().equals(client)) {
                        throw new IllegalArgumentException(
                                "Client " + client.getLastName() +
                                        " already has a reservation for this connection"
                        );
                    }
                }
                
                trip.addReservation(reservation);
            }

            trip = tripRepository.save(trip);
            
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

        Optional<Client> clientOpt = clientRepository.findById(id);
        if (clientOpt.isEmpty() || !clientOpt.get().getLastName().equalsIgnoreCase(lastName)) {
            System.out.println("No trips found for " + lastName + " with ID " + id);
            return;
        }

        Client client = clientOpt.get();

        List<Reservation> reservations = reservationRepository.findAll().stream()
                .filter(r -> r.getClient().getId().equals(client.getId()))
                .toList();

        if (reservations.isEmpty()) {
            System.out.println("No trips found for " + lastName + " with ID " + id);
            return;
        }

        Set<Long> tripIds = new HashSet<>();
        for (Reservation r : reservations) {
            if (r.getTrip() != null) {
                tripIds.add(r.getTrip().getTripId());
            }
        }

        List<Trip> allTrips = tripRepository.findAllById(tripIds);

        for (Trip trip : allTrips) {
            Connection connection = findConnectionByRouteId(trip.getConnectionRouteId());
            if (connection != null) {
                trip.setConnection(connection);
            }
            
            // Also reconstruct connections for each reservation
            for (Reservation reservation : trip.getReservations()) {
                Connection resConnection = findConnectionByRouteId(reservation.getConnectionRouteId());
                if (resConnection != null) {
                    reservation.setConnection(resConnection);
                }
            }
        }

        List<Trip> currentTrips = new ArrayList<>();
        List<Trip> pastTrips = new ArrayList<>();

        for (Trip trip : allTrips) {
            if (trip.isCurrent()) {
                currentTrips.add(trip);
            } else {
                pastTrips.add(trip);
            }
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

    private Connection findConnectionByRouteId(String routeId) {
        if (routeId == null) return null;
        
        List<Connection> allConnections = catalog.getAllConnections();
        for (Connection conn : allConnections) {
            if (conn.getRouteId().equals(routeId)) {
                return conn;
            }
        }
        return null;
    }

    public List<Connection> getResultsList() {
        return resultsList;
    }

    public ConnectionCatalog getCatalog() {
        return catalog;
    }

    public void showResults() {
        for (Connection connection : resultsList) {
            System.out.println(connection);
        }
    }
}