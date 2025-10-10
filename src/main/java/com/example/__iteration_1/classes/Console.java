package com.example.__iteration_1.classes;
import com.example.__iteration_1.enums.DaysOfOperation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Console {

    private final String csv = "src/main/resources/eu_rail_network.csv";
    private static final String[] DAY_ORDER = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private List<Connection> resultsList = new ArrayList();
    private ConnectionCatalog catalog;

    public Console() {
        this.catalog = new ConnectionCatalog();
    }

    public void readFile() {
        catalog.readFile(csv);
    }

    public List<Connection> showResults() {
        return resultsList = catalog.showResults();
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


    public void printResults() {
        System.out.printf(
                "| %-30s | %-30s | %-30s | %-40s | %-40s | %-40s | %-40s |\n",
                "Departure", "Arrival", "Timetable", "Train", "Days", "1st Class", "2nd Class"
        );
        System.out.println("-------------------------------------------------------------------------------------------");
        for (Connection connection : resultsList) {
            System.out.println(connection);
        }
    }

}


