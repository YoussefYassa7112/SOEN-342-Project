package com.example.__iteration_1.classes;

import com.example.__iteration_1.enums.DaysOfOperation;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Connection {

    @Id
    @Column(name = "route_id")
    private String routeId = UUID.randomUUID().toString();;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "departure_city_name", referencedColumnName = "city_name")
    City departureCity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "arrival_city_name", referencedColumnName = "city_name")
    City arrivalCity;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "timetable_id")
    Timetable timetable;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "train_id")
    Train train;

    @ElementCollection(targetClass = DaysOfOperation.class)
    @CollectionTable(name = "connection_days_of_operation", joinColumns = @JoinColumn(name = "connection_id"))
    @Column(name = "day_of_operation")
    List<DaysOfOperation> daysOfOperation;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "first_class_ticket_id")
    FirstClassTicket firstClassTicket;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "second_class_ticket_id")
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

    public Connection() {

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
                "| %-50s | %-50s | %-50s | %-50s | %-50s | %-50s |",
                departureCity,
                arrivalCity,
                timetable,
                train,
                firstClassTicket,
                secondClassTicket
        );
    }
}
