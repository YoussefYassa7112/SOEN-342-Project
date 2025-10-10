package com.project342._342proj;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalTime;

public class Console {
    String csvFileName = "src/main/resources/eu_rail_network.csv";

    public Connection readCSV(){

        try(Scanner input = new Scanner(new FileInputStream(csvFileName))){

            input.nextLine();

            while(input.hasNextLine()){
                String line = input.nextLine();
                System.out.println(line);

                String[] data = line.split(",");


                String routeId = data[0];
                City departingCity = new City(data[1]);
                City arrivalCity = new City(data[2]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime departingTime = LocalTime.parse(data[3], formatter);

                String arrivalTimeSt = data[4];
                if(arrivalTimeSt.endsWith(" (+1d)")){
                    arrivalTimeSt = arrivalTimeSt.replace(" (+1d)", "");
                }

                LocalTime arrivalTime = LocalTime.parse(arrivalTimeSt, formatter);

                TimeTable timeTable = new TimeTable(departingTime, arrivalTime);
                Train train = new Train(data[5]);
//              DaysOfOperation daysOfOperation = new DaysOfOperation(data[6]);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
