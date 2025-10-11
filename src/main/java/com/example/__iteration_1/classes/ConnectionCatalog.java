package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConnectionCatalog {

    List<Connection> connections;
    private static final String[] DAY_ORDER = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private List<Connection> resultsList = new ArrayList();
    private List<Connection> direct = new ArrayList<>();

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
            direct.add(connection);
        }
    }

//    public void showResults(){
//        resultsList.forEach(connection -> {System.out.println(connection);});
//    }

    List<Connection> newConnections = new ArrayList<>();
    public void transitive() {

        for (Connection c1 : resultsList) {
            for (Connection c2 : resultsList) {
                if ((c1 != c2 && c1.getArrivalCity().equals(c2.getDepartureCity())) && c2.timetable.getDepartureTime().isAfter(c1.timetable.getArrivalTime())) {
                    newConnections.add(new Connection(c1, c2));
                }
            }
        }

        resultsList.addAll(newConnections);

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

        resultsList.addAll(twoStopConnections);
        System.out.println("Added " + twoStopConnections.size() + " 2-stop connections.");
    }





    public void showResults(){
        resultsList.forEach(connection -> {System.out.println(connection);});
    }

}
