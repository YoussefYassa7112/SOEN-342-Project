package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class Timetable {
    LocalTime departureTime;
    LocalTime arrivalTime;
    Duration duration;

    public Timetable(String departureTime, String arrivalTime) {
        this.departureTime = LocalTime.parse(departureTime.substring(0, 5));
        this.arrivalTime = LocalTime.parse(arrivalTime.substring(0, 5));

        if(arrivalTime.contains("+1")) {
            duration = Duration.between(this.arrivalTime ,this.departureTime);
            duration.plusMinutes(1440);
        }
        else{
            duration = Duration.between(this.departureTime ,this.arrivalTime);
        }

    }

    @Override
    public String toString() {
        return "Timetable{" +
                "departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", duration=" + duration +
                '}';
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
