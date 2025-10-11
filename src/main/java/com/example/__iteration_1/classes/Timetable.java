package com.example.__iteration_1.classes;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class Timetable {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Long duration;

    public Timetable(String departureTime, String arrivalTime) {
        this.departureTime = LocalTime.parse(departureTime.substring(0, 5));
        this.arrivalTime = LocalTime.parse(arrivalTime.substring(0, 5));

        long depMinutes = this.departureTime.getHour() * 60L + this.departureTime.getMinute();
        long arrMinutes = this.arrivalTime.getHour() * 60L + this.arrivalTime.getMinute();
        if(arrivalTime.contains("+1")) {
            duration=24L*60L-depMinutes+arrMinutes;
        }
        else{
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