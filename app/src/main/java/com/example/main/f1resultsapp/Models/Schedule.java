package com.example.main.f1resultsapp.Models;

public class Schedule {
    String round, raceName, circuit, location, country, time, date;

    public Schedule(String name, String raceName, String circuit, String location, String country, String date, String time) {
        this.round = name;
        this.raceName = raceName;
        this.circuit = circuit;
        this.location = location;
        this.country = country;
        this.time = time;
        this.date = date;
    }

    public String getRound() { return round; }
    public String getRaceName() {
        return raceName;
    }
    public String getCircuit() {
        return circuit;
    }
    public String getLocation() {
        return location;
    }
    public String getCountry() {
        return country;
    }
    public String getDate() {
        return date;
    }
    public String getTime() { return time; }
}
