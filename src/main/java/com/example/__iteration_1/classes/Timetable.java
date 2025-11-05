package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class Timetable {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Long duration;

    public Timetable(String departureTimeStr, String arrivalTimeStr) {

        this.departureTime = LocalTime.parse(departureTimeStr.substring(0, 5));
        this.arrivalTime   = LocalTime.parse(arrivalTimeStr.substring(0, 5));

        long depMinutes = this.departureTime.toSecondOfDay() / 60;
        long arrMinutes = this.arrivalTime.toSecondOfDay() / 60;

        if (arrivalTimeStr.contains("+1") || arrMinutes < depMinutes) {
            // arrival is next day
            duration = (24 * 60 - depMinutes) + arrMinutes;
        } else {
            duration = arrMinutes - depMinutes;
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

    public Long getDuration() {
        return duration;
    }


}
