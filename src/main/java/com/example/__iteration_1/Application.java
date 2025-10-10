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
//		c.printResults();
//		System.out.println("Hello World");
//		c.showResults();
//		c.sortResultsByTripDuration("asc");
//		c.printResults();

		//c.searchConnection();
		c.searchConnection("duration", "140");
		for(int i = 0; i<c.getResultsList().size(); i++){
			System.out.println(c.getResultsList().get(i));
		}
	}

}
