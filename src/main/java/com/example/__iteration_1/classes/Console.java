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
        resultsList = catalog.getResults();
    }

    public void oneStop(){
        catalog.transitive();
    }

    public void twoStops(){
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
                "| %-30s | %-30s | %-30s | %-40s | %-40s | %-40s | %-40s |\n",
                "Departure", "Arrival", "Timetable", "Train", "Days", "1st Class", "2nd Class"
        );
        System.out.println("-------------------------------------------------------------------------------------------");
        for (Connection connection : resultsList) {
            System.out.println(connection);
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

    public void resetSearch(){
        resultsList = catalog.resetFilters();
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