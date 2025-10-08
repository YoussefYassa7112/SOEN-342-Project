package com.example.__iteration_1.classes;
import com.example.__iteration_1.enums.DaysOfOperation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Console {

    private final String csv = "src/main/resources/eu_rail_network.csv";
    private static final String[] DAY_ORDER = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    public void readFile() {

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
            //LocalTime departure = LocalTime.parse(values[3], DateTimeFormatter.ofPattern("HH:mm"));
            // LocalTime arrival = LocalTime.parse(values[4], DateTimeFormatter.ofPattern("HH:mm"));
            //Timetable timetable = new Timetable(departure, arrival);
            Train train = new Train(values[5]);

            String days = null;
            int i = 6;
            if(values[6].startsWith("\"")){
                days = values[6];
                do{
                    i++;
                    days += "," + values[i];
                }while(!values[i].endsWith("\""));
                days = days.substring(1, days.length()-1);
            }
            else{
                days = values[6];
            }
            System.out.println(days);

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
            FirstClassTicket firstClassTicket = new FirstClassTicket(Double.parseDouble(values[values.length-2]));
            SecondClassTicket secondClassTicket = new SecondClassTicket(Double.parseDouble(values[values.length-1]));


        }
        scanner.close();
    }
}


