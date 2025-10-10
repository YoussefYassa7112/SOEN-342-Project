package com.example.__iteration_1;

import com.example.__iteration_1.classes.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);

			Console c = new Console();
			c.readFile();
			System.out.println("Welcome to the Train Ticketing System!");
		while(true) {
			System.out.println("Please choose an option:");
			System.out.println("1. View available connections");
			System.out.println("2. Search for a connection");
			System.out.println("3. Sort connections by price");
			System.out.println("4. Sort connections by duration");
			System.out.println("5. Exit");
		}

	}

}
