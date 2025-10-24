package com.example.__iteration_1;

import com.example.__iteration_1.classes.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);

        Console c = new Console();
        c.readFile();
        c.oneStop();
        c.twoStops();
        System.out.println("Welcome to the Train Ticketing System!");

        while(true){
            System.out.println("Please select an option:");
            System.out.println("1. View all Connections");
            System.out.println("2. Search specific connections");
            System.out.println("3. Sort connections by price");
            System.out.println("4. Sort connections by duration");
            System.out.println("5. Reset search");
            System.out.println("6. Exit");

            Scanner scanner = new Scanner(System.in);
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    c.searchConnection();
                    break;
                case "2":
                    System.out.println("Enter parameter to search by (departureCity/arrivalCity/departureTime/arrivalTime/daysOfOperation):");
                    String parameter = scanner.nextLine();
                    System.out.println("Enter value to search (time-> hh:mm, days-> MON,TUE,WEN,THU,FRI,SAT,SUN):");
                    String value = scanner.nextLine();
                    c.searchConnection(parameter, value);
                    if (c.getCatalog().getResultsList().isEmpty()) {
                        System.out.println("No connections found matching the criteria.");
                    } else{
                        c.showResults();
                    }
                    break;
                case "3":
                    c.sortResultsByPrice();
                    c.showResults();
                    break;
                case "4":
                    c.sortResultsByTripDuration();
                    c.showResults();
                    break;
                case "5":
                    c.resetSearch();
                    System.out.println("Search reset. Displaying all connections.");
                    c.showResults();
                    break;
                case "6":
                    System.out.println("Thank you for using the Train Ticketing System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }

}