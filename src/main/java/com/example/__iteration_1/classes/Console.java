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

    public List<Connection> sortResultsByTripDuration(String order) {
        Comparator<Connection> comparator = Comparator.comparingLong(
                c -> c.getTimetable().getDuration()
        );
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        resultsList.sort(comparator);
        return resultsList;
    }


    public void printResults() {
        for (Connection connection : resultsList) {
            System.out.println(connection);
        }
    }

    public void searchConnection(){
        resultsList = catalog.getAllConnections();
    }

    public void searchConnection(String parameter, String value){
        switch(parameter){
            case "departureCity":
                resultsList.clear();
                resultsList = catalog.getConnectionsByDepartureCity(value);
                break;
            case "arrivalCity":
                resultsList = catalog.getConnectionsByArrivalCity(value);
                break;
            case "dayOfOperation":
                resultsList = catalog.getConnectionsByDayOfOperation(DaysOfOperation.valueOf(value));
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

    public List<Connection> getResultsList() {
        return resultsList;
    }



}


