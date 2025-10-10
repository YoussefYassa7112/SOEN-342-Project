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



}


