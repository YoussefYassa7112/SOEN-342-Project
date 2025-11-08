package com.example.__iteration_1.classes;

import jakarta.persistence.*;


@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "city_name")
    private String name;

    public City(String name) {
        this.name = name;
    }

    public City() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equalsIgnoreCase(city.name); // compare names
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
