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
        System.out.println("========================================");
        System.out.println("Welcome to the Train Ticketing System!");
        System.out.println("========================================");

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View all Connections");
            System.out.println("2. Search specific connections");
            System.out.println("3. Sort connections by price");
            System.out.println("4. Sort connections by duration");
            System.out.println("5. Reset search");
            System.out.println("6. Book a Trip");
            System.out.println("7. View My Trips");
            System.out.println("8. Exit");
            System.out.print("Please select an option: ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    c.searchConnection();
                    break;

                case "2":
                    System.out.println("Enter parameter to search by:");
                    System.out.println("  - departureCity");
                    System.out.println("  - arrivalCity");
                    System.out.println("  - departureTime");
                    System.out.println("  - arrivalTime");
                    System.out.println("  - dayOfOperation");
                    System.out.print("Parameter: ");
                    String parameter = scanner.nextLine();
                    System.out.print("Enter value (time format: HH:mm, days: MON/TUE/WED/THU/FRI/SAT/SUN): ");
                    String value = scanner.nextLine();
                    c.searchConnection(parameter, value);
                    if (c.getCatalog().getResults().isEmpty()) {
                        System.out.println("No connections found matching the criteria.");
                    } else{
                        c.showResults();
                    }
                    break;

                case "3":
                    if (c.getResultsList().isEmpty()) {
                        System.out.println("No connections to sort. Please search first.");
                    } else {
                        c.sortResultsByPrice();
                        c.showResults();
                    }
                    break;

                case "4":
                    if (c.getResultsList().isEmpty()) {
                        System.out.println("No connections to sort. Please search first.");
                    } else {
                        c.sortResultsByTripDuration();
                        c.showResults();
                    }
                    break;

                case "5":
                    c.resetSearch();
                    System.out.println("Search reset. Displaying all connections.");
                    c.showResults();
                    break;

                case "6":
                    c.bookTrip(scanner);
                    break;

                case "7":
                    c.viewTrips(scanner);
                    break;

                case "8":
                    System.out.println("\nThank you for using the Train Ticketing System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}