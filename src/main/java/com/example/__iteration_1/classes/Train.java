package com.example.__iteration_1.classes;

public class Train {

    private String trainType;

    public Train(String trainType) {
        this.trainType = trainType;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    @Override
    public String toString() {
        return "Train{" +
                ", trainType='" + trainType + '\'' +
                '}';
    }
}
