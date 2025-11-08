package com.example.__iteration_1.classes;

import jakarta.persistence.*;

@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private long id;

    @Column(name = "train_type")
    private String trainType;

    public Train(String trainType) {
        this.trainType = trainType;
    }

    public Train(Train t1, Train t2) {
        this.trainType = t1.getTrainType() + " + " + t2.getTrainType();
    }

    public Train() {

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
