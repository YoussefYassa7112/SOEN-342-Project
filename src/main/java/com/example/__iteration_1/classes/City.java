package com.example.__iteration_1.classes;

public class City {

    private String name;

    public City(String name) {
        this.name = name;
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
        if (this == o) return true;              // same reference
        if (o == null || getClass() != o.getClass()) return false; // null or different class
        City city = (City) o;
        return name.equalsIgnoreCase(city.name); // compare names
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
