package com.example.__iteration_1;

import com.example.__iteration_1.classes.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);

        Console c = new Console();
        c.readFile();
        c.oneStop();
        c.twoStop();
        System.out.println("Welcome to the Train Ticketing System!");

        //c.searchConnection("arrivalCity", "Dublin");
        c.showResults();
        c.sortResultsByTripDuration();
        c.showResults();
        c.sortResultsByPrice();
        c.showResults();
        System.out.println("---------------------------------------------------");
        c.searchConnection("arrivalCity", "Brighton");
        c.showResults();


    }

}
