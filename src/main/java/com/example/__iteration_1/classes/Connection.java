package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Connection {

    String routeId;
    City departureCity;
    City arrivalCity;
    Timetable timetable;
    Train train;
    List<DaysOfOperation> daysOfOperation;
    FirstClassTicket firstClassTicket;
    SecondClassTicket secondClassTicket;

    public Set<DaysOfOperation> getDaysOfOperation() {
        return new HashSet<>(daysOfOperation);
    }

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

    public Connection(Connection c1, Connection c2) {

        this.departureCity = c1.getDepartureCity();

        this.arrivalCity = c2.getArrivalCity();

        this.timetable = new Timetable(
                c1.getTimetable().getDepartureTime().toString(),
                c2.getTimetable().getArrivalTime().toString()
        );


        this.train = new Train(c1.getTrain().getTrainType() + " + " + c2.getTrain().getTrainType());


        Set<DaysOfOperation> common = new HashSet<>();
        if (c1.getDaysOfOperation() != null) common.addAll(c1.getDaysOfOperation());
        if (c2.getDaysOfOperation() != null) common.retainAll(c2.getDaysOfOperation());

        if (common.isEmpty() && c1.getDaysOfOperation() != null) {
            common.addAll(c1.getDaysOfOperation());
        }

        this.daysOfOperation = new ArrayList<>(common);


        this.firstClassTicket = new FirstClassTicket(
                c1.getFirstClassTicket().getPrice() + c2.getFirstClassTicket().getPrice()
        );
        this.secondClassTicket = new SecondClassTicket(
                c1.getSecondClassTicket().getPrice() + c2.getSecondClassTicket().getPrice()
        );

        this.routeId = c1.getRouteId() + "-" + c2.getRouteId();
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

//    public DaysOfOperation getDaysOfOperation() {
//        return daysOfOperation;
//    }
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
