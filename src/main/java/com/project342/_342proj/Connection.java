package com.project342._342proj;

public class Connection {

    private String routeID;
    private City departingCity;
    private City arrivingCity;
    private TimeTable timeTable;
    private Train train;
    private DaysOfOperation daysOfOperation;
    private FirstClassTicket firstClassTicket;
    private SecondClassTicket secondClassTicket;

    public Connection(String routeID, City departingCity, City arrivingCity, TimeTable timeTable, Train train, DaysOfOperation daysOfOperation, FirstClassTicket firstClassTicket, SecondClassTicket secondClassTicket) {
        this.routeID = routeID;
        this.departingCity = departingCity;
        this.arrivingCity = arrivingCity;
        this.timeTable = timeTable;
        this.train = train;
        this.daysOfOperation = daysOfOperation;
        this.firstClassTicket = firstClassTicket;
        this.secondClassTicket = secondClassTicket;
    }


    public City getDepartingCity() {
        return departingCity;
    }

    public void setDepartingCity(City departingCity) {
        this.departingCity = departingCity;
    }

    public City getArrivingCity() {
        return arrivingCity;
    }

    public void setArrivingCity(City arrivingCity) {
        this.arrivingCity = arrivingCity;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public DaysOfOperation getDaysOfOperation() {
        return daysOfOperation;
    }

    public void setDaysOfOperation(DaysOfOperation daysOfOperation) {
        this.daysOfOperation = daysOfOperation;
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
}
