package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.*;

public class ConnectionCatalog {

    private List<Connection> connections;
    private static final String[] DAY_ORDER = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private List<Connection> resultsList;
    private List<Connection> direct;


    public ConnectionCatalog() {
        connections = new ArrayList<>();
        resultsList = new ArrayList<>();
        direct = new ArrayList<>();
    }

    public void readFile(String csv) {

        Scanner scanner = null;

        try {
            scanner = new Scanner(new FileInputStream(csv));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] values = line.split(",");

            String routeId = values[0];
            City departureCity = new City(values[1]);
            City arrivalCity = new City(values[2]);
            String departure = values[3];
            String arrival = values[4];
            Timetable timetable = new Timetable(departure, arrival);
            Train train = new Train(values[5]);

            String days = null;
            int i = 6;
            if (values[6].startsWith("\"")) {
                days = values[6];
                do {
                    i++;
                    days += "," + values[i];
                } while (!values[i].endsWith("\""));
                days = days.substring(1, days.length() - 1);
            } else {
                days = values[6];
            }

            List<DaysOfOperation> result = new ArrayList<>();
            for (String part : days.split(",")) {
                part = part.trim().toUpperCase();
                if (part.equals("DAILY")) {
                    for (String day : DAY_ORDER) {
                        result.add(DaysOfOperation.valueOf(day));
                    }
                } else if (Arrays.asList(DAY_ORDER).contains(part)) {
                    result.add(DaysOfOperation.valueOf(part));
                } else if (part.contains("-")) {
                    String[] range = part.split("-");
                    String startDay = range[0].trim().toUpperCase();
                    String endDay = range[1].trim().toUpperCase();
                    int start = Arrays.asList(DAY_ORDER).indexOf(startDay);
                    int end = Arrays.asList(DAY_ORDER).indexOf(endDay);
                    if (start != -1 && end != -1 && start <= end) {
                        for (int k = start; k <= end; k++) {
                            result.add(DaysOfOperation.valueOf(DAY_ORDER[k]));
                        }
                    }
                }
            }
            FirstClassTicket firstClassTicket = new FirstClassTicket(Double.parseDouble(values[values.length - 2]));
            SecondClassTicket secondClassTicket = new SecondClassTicket(Double.parseDouble(values[values.length - 1]));

            Connection connection = new Connection(routeId, departureCity, arrivalCity, timetable, train, result, firstClassTicket, secondClassTicket);
            connections.add(connection);
            resultsList.add(connection);
            direct.add(connection);
        }
        scanner.close();
    }


    List<Connection> newConnections = new ArrayList<>();
    public void transitive() {

        for (Connection c1 : direct) {
            for (Connection c2 : direct) {
                if ((c1 != c2 && c1.getArrivalCity().equals(c2.getDepartureCity())) && c2.timetable.getDepartureTime().isAfter(c1.timetable.getArrivalTime())) {
                    newConnections.add(new Connection(c1, c2));
                }
            }
        }

        connections.addAll(newConnections);

        System.out.println("Added " + newConnections.size() + " transitive connections.");
    }


    private boolean isValidTime(Connection first, Connection second) {
        long arrival = first.getTimetable().getArrivalTime().toSecondOfDay();
        long departure = second.getTimetable().getDepartureTime().toSecondOfDay();

        if (departure < arrival) departure += 24 * 3600; // overnight
        return departure >= arrival;
    }


    public void transitiveTwoStops() {
        List<Connection> twoStopConnections = new ArrayList<>();

        for (Connection c1 : direct) {
            for (Connection c2 : direct) {
                // 1-stop check: c1 → c2
                if (c1 != c2 &&
                        c1.getArrivalCity().equals(c2.getDepartureCity()) &&
                        isValidTime(c1, c2) &&
                        !c1.getDaysOfOperation().isEmpty() &&
                        !c2.getDaysOfOperation().isEmpty()) {

                    Connection oneStop = new Connection(c1, c2);

                    for (Connection c3 : direct) {
                        // 2-stop check: c2 → c3
                        if (c3 != c1 && c3 != c2 &&
                                c2.getArrivalCity().equals(c3.getDepartureCity()) &&
                                isValidTime(c2, c3) &&
                                !c3.getDaysOfOperation().isEmpty() && c3.timetable.getDepartureTime().isAfter(oneStop.timetable.getArrivalTime())){

                            // Create a 2-stop connection: c1 → c2 → c3
                            twoStopConnections.add(new Connection(oneStop, c3));
                        }
                    }
                }
            }
        }

        connections.addAll(twoStopConnections);
        System.out.println("Added " + twoStopConnections.size() + " 2-stop connections.");
    }


    public List<Connection> showResults(){
        for (Connection connection : resultsList) {
            System.out.println(connection);
        }
        return resultsList;
    }

    public List<Connection> getAllConnections(){
        for (Connection connection : connections) {
            System.out.println(connection);
        }
        return connections;
    }


    public List<Connection> getConnectionsByDepartureCity(String value) {
        resultsList.clear();
        for (Connection connection : connections) {
            if (connection.getDepartureCity().getName().equalsIgnoreCase(value)) {
                resultsList.add(connection);
            }
        }
        return resultsList;
    }

    public List<Connection> getConnectionsByArrivalCity(String value) {
        resultsList.clear();
        for (Connection connection : connections) {
            if (connection.getArrivalCity().getName().equalsIgnoreCase(value)) {
                resultsList.add(connection);
            }
        }
        return resultsList;
    }

    public List<Connection> getConnectionsByDayOfOperation(DaysOfOperation dayOfOperation) {
        resultsList.clear();
        for (Connection connection : connections) {
            if (connection.getDaysOfOperation().contains(dayOfOperation)) {
                resultsList.add(connection);
            }
        }
        return resultsList;
    }

    public List<Connection> getConnectionsByDepartureTime(LocalTime time) {
        resultsList.clear();
        for (Connection connection : connections) {
            if (connection.getTimetable().getDepartureTime().equals(time)) {
                resultsList.add(connection);
            }
        }
        return resultsList;
    }

    public List<Connection> getConnectionsByArrivalTimeBefore(LocalTime time) {
        resultsList.clear();
        for (Connection connection : connections) {
            if (connection.getTimetable().getArrivalTime().equals(time)) {
                resultsList.add(connection);
            }
        }
        return resultsList;
    }

    public List<Connection> sortResultsByTripDuration() {
        System.out.println("Would you like to sort by trip duration in ascending or descending order? (Enter 'asc' or 'desc'): ");
        Scanner scanner = new Scanner(System.in);
        String order = scanner.nextLine().trim().toLowerCase();
        if (!order.equals("asc") && !order.equals("desc")) {
            System.out.println("Invalid input. Default to ascending order.");
            order = "asc";
        }
        Comparator<Connection> comparator = Comparator.comparingLong(
                c -> c.getTimetable().getDuration()
        );
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        resultsList.sort(comparator);
        return resultsList;
    }
    public List<Connection> sortResultsByPrice() {
        while(true) {
            System.out.print("Would you like to sort by First Class or Second Class ticket price? (Enter '1' or '2'): ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
            System.out.println("Would you like to sort in ascending or descending order? (Enter 'asc' or 'desc'): ");
            String order = scanner.nextLine().trim().toLowerCase();
            if (!order.equals("asc") && !order.equals("desc")) {
                System.out.println("Invalid input. Default to ascending order.");
                order = "asc";
            }
            Comparator<Connection> comparator;
            if (choice.equals("1")) {
                comparator = Comparator.comparingDouble(c -> c.getFirstClassTicket().getPrice());
                if ("desc".equalsIgnoreCase(order)) {
                    comparator = comparator.reversed();
                }
                resultsList.sort(comparator);
                return resultsList;
            } else if (choice.equals("2")) {
                comparator = Comparator.comparingDouble(c -> c.getSecondClassTicket().getPrice());
                if ("desc".equalsIgnoreCase(order)) {
                    comparator = comparator.reversed();
                }
                resultsList.sort(comparator);
                return resultsList;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public List<Connection> getConnections() {
        resultsList.addAll(connections);
        return resultsList;
    }

    public List<Connection> getResultsList() {
        return resultsList;
    }
}