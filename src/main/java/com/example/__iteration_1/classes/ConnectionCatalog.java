package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConnectionCatalog {

    private List<Connection> connections;
    private static final String[] DAY_ORDER = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private List<Connection> resultsList = new ArrayList();

    public ConnectionCatalog() {
        connections = new ArrayList<>();
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
            resultsList.add(connection);
        }
    }

    public List<Connection> showResults(){
        return resultsList;
    }

    public List<Connection> getAllConnections(){
        return resultsList;
    }


    public List<Connection> getConnectionsByDepartureCity(String value) {
        connections.clear();
        for (Connection connection : resultsList) {
            if (connection.getDepartureCity().getName().equalsIgnoreCase(value)) {
                connections.add(connection);
            }
        }
        return connections;
    }

    public List<Connection> getConnectionsByArrivalCity(String value) {
        connections.clear();
        for (Connection connection : resultsList) {
            if (connection.getArrivalCity().getName().equalsIgnoreCase(value)) {
                connections.add(connection);
            }
        }
        return connections;
    }

    public List<Connection> getConnectionsByDayOfOperation(DaysOfOperation dayOfOperation) {
        connections.clear();
        for (Connection connection : resultsList) {
            if (connection.getDaysOfOperation().contains(dayOfOperation)) {
                connections.add(connection);
            }
        }
        return connections;
    }

    public List<Connection> getConnectionsByDepartureTime(LocalTime time) {
        connections.clear();
        for (Connection connection : resultsList) {
            if (connection.getTimetable().getDepartureTime().equals(time)) {
                connections.add(connection);
            }
        }
        return connections;
    }

    public List<Connection> getConnectionsByArrivalTimeBefore(LocalTime time) {
        connections.clear();
        for (Connection connection : resultsList) {
            if (connection.getTimetable().getArrivalTime().equals(time)) {
                connections.add(connection);
            }
        }
        return connections;
    }
}
