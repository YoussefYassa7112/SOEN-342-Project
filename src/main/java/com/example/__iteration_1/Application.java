package com.example.__iteration_1;

import com.example.__iteration_1.classes.Console;
import com.example.__iteration_1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        TripRepository tripRepository = applicationContext.getBean(TripRepository.class);
        ClientRepository clientRepository = applicationContext.getBean(ClientRepository.class);
        ReservationRepository reservationRepository = applicationContext.getBean(ReservationRepository.class);
        TicketRepository ticketRepository = applicationContext.getBean(TicketRepository.class);

        Console c = new Console(tripRepository, clientRepository, reservationRepository, ticketRepository);
        c.readFile();
        c.oneStop();
        c.twoStops();
        
        System.out.println("\n  ╔════════════════════════════════════════════════════════╗");
        System.out.println("  ║            RAIL NETWORK BOOKING SYSTEM                 ║");
        System.out.println("  ╚════════════════════════════════════════════════════════╝");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n┌──────────────────────────────────────┐");
            System.out.println("│             MAIN MENU                │");
            System.out.println("├──────────────────────────────────────┤");
            System.out.println("│  1. View All Connections             │");
            System.out.println("│  2. Search Connections               │");
            System.out.println("│  3. Sort by Price                    │");
            System.out.println("│  4. Sort by Duration                 │");
            System.out.println("│  5. Reset Search                     │");
            System.out.println("│  6. Book a Trip                      │");
            System.out.println("│  7. View My Trips                    │");
            System.out.println("│  8. Exit                             │");
            System.out.println("└──────────────────────────────────────┘");
            System.out.print("Select an option: ");

            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    c.searchConnection();
                    break;

                case "2":
                    System.out.println("\n┌─────────────────────────────────────────┐");
                    System.out.println("│          SEARCH PARAMETERS              │");
                    System.out.println("├─────────────────────────────────────────┤");
                    System.out.println("│  departureCity    - City of departure   │");
                    System.out.println("│  arrivalCity      - City of arrival     │");
                    System.out.println("│  departureTime    - Time (HH:mm)        │");
                    System.out.println("│  arrivalTime      - Time (HH:mm)        │");
                    System.out.println("│  dayOfOperation   - Day of week         │");
                    System.out.println("└─────────────────────────────────────────┘");
                    System.out.print("Enter parameter: ");
                    String parameter = scanner.nextLine();
                    System.out.print("Enter value: ");
                    String value = scanner.nextLine();
                    c.searchConnection(parameter, value);
                    if (c.getCatalog().getResults().isEmpty()) {
                        System.out.println("\n  No connections found matching the criteria.");
                    } else {
                        c.showResults();
                    }
                    break;

                case "3":
                    if (c.getResultsList().isEmpty()) {
                        System.out.println("\n  No connections to sort. Please search first.");
                    } else {
                        c.sortResultsByPrice();
                        c.showResults();
                    }
                    break;

                case "4":
                    if (c.getResultsList().isEmpty()) {
                        System.out.println("\n  No connections to sort. Please search first.");
                    } else {
                        c.sortResultsByTripDuration();
                        c.showResults();
                    }
                    break;

                case "5":
                    c.resetSearch();
                    System.out.println("\n  Search reset successfully.");
                    c.showResults();
                    break;

                case "6":
                    c.bookTrip();
                    break;

                case "7":
                    c.viewTrips();
                    break;

                case "8":
                    System.out.println("\n  ╔════════════════════════════════════════════════════════╗");
                    System.out.println("  ║         Thank you for using Rail Network!              ║");
                    System.out.println("  ║                 Have a safe journey!                   ║");
                    System.out.println("  ╚════════════════════════════════════════════════════════╝");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("\n  Invalid option. Please try again.");
            }
        }
    }
}