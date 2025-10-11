package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;

import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Connection {

    private String routeId;
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

    public Connection(Connection c1, Connection c2) {
        // departure of the first
        this.departureCity = c1.getDepartureCity();
        // arrival of the second
        this.arrivalCity = c2.getArrivalCity();

        // timetable: start from c1’s departure and end at c2’s arrival
        this.timetable = new Timetable(
                c1.getTimetable().getDepartureTime().toString(),
                c2.getTimetable().getArrivalTime().toString()
        );

        // if both trains are different, store a combined label
        this.train = new Train(c1.getTrain().getTrainType() + " + " + c2.getTrain().getTrainType());

        // combine operating days by finding the intersection
        Set<DaysOfOperation> commonDays = new HashSet<>(c1.getDaysOfOperation());
        commonDays.retainAll(c2.getDaysOfOperation());

        this.daysOfOperation = new ArrayList<>(commonDays);


        // combine ticket prices
        this.firstClassTicket = new FirstClassTicket(
                c1.getFirstClassTicket().getPrice() + c2.getFirstClassTicket().getPrice()
        );
        this.secondClassTicket = new SecondClassTicket(
                c1.getSecondClassTicket().getPrice() + c2.getSecondClassTicket().getPrice()
        );

        // optionally create a new route ID for clarity
        this.routeId = c1.getRouteId() + "-" + c2.getRouteId();
    }


    public Connection(Connection c1, Connection c2, Connection c3) {
        this.departureCity = c1.getDepartureCity();
        this.arrivalCity = c3.getArrivalCity();

        // Combine timetable start and end
        this.timetable = new Timetable(
                c1.getTimetable().getDepartureTime().toString(),
                c3.getTimetable().getArrivalTime().toString()
        );

        String train1 = c1.getTrain().getTrainType();
        String train2 = c2.getTrain().getTrainType();
        String train3 = c3.getTrain().getTrainType();

        this.train = new Train(train1 + " + " + train2 + " + " + train3);

        // Combine days (intersection of all)
        Set<DaysOfOperation> common = new HashSet<>(c1.getDaysOfOperation());
        common.retainAll(c2.getDaysOfOperation());
        common.retainAll(c3.getDaysOfOperation());
        this.daysOfOperation = new ArrayList<>(common);

        // Sum ticket prices
        this.firstClassTicket = new FirstClassTicket(
                c1.getFirstClassTicket().getPrice() +
                        c2.getFirstClassTicket().getPrice() +
                        c3.getFirstClassTicket().getPrice()
        );

        this.secondClassTicket = new SecondClassTicket(
                c1.getSecondClassTicket().getPrice() +
                        c2.getSecondClassTicket().getPrice() +
                        c3.getSecondClassTicket().getPrice()
        );

        this.routeId = c1.getRouteId() + "-" + c2.getRouteId() + "-" + c3.getRouteId();
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
        return String.format(
                "| %-30s | %-30s | %-30s | %-30s | %-40s | %-40s | %-40s |",
                departureCity,
                arrivalCity,
                timetable,
                train,
                daysOfOperation,
                firstClassTicket,
                secondClassTicket
        );
    }
}
