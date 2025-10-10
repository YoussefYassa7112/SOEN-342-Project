package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.util.List;

public class Connection {

    String routeId;
    City departureCity;
    City arrivalCity;
    Timetable timetable;
    Train train;
    List<DaysOfOperation> daysOfOperation;
    FirstClassTicket firstClassTicket;
    SecondClassTicket secondClassTicket;

    public Connection(String routeId, City departureCity, City arrivalCity, Timetable timetable, Train train, List<DaysOfOperation> daysOfOperation, FirstClassTicket firstClassTicket, SecondClassTicket secondClassTicket) {
        this.routeId = routeId;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.timetable = timetable;
        this.train = train;
        this.daysOfOperation = daysOfOperation;
        this.firstClassTicket = firstClassTicket;
        this.secondClassTicket = secondClassTicket;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public City getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(City departureCity) {
        this.departureCity = departureCity;
    }

    public City getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(City arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<DaysOfOperation> getDaysOfOperation() {
        return daysOfOperation;
    }
//
//    public void setDaysOfOperation(DaysOfOperation daysOfOperation) {
//        this.daysOfOperation = daysOfOperation;
//    }

    public FirstClassTicket getFirstClassTicket() {
        return firstClassTicket;
    }

    public void setFirstClassTicket(FirstClassTicket firstClassTicket) {
        this.firstClassTicket = firstClassTicket;
    }

    public SecondClassTicket getSecondClassTicket() {
        return secondClassTicket;
    }

    public void setSecondClassTicket(SecondClassTicket secondClassTicket) {
        this.secondClassTicket = secondClassTicket;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "routeId='" + routeId + '\'' +
                ", departureCity=" + departureCity +
                ", arrivalCity=" + arrivalCity +
                ", timetable=" + timetable +
                ", train=" + train +
                ", daysOfOperation=" + daysOfOperation +
                ", firstClassTicket=" + firstClassTicket +
                ", secondClassTicket=" + secondClassTicket +
                '}';
    }
}
