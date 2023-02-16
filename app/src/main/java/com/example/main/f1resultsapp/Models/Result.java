package com.example.main.f1resultsapp.Models;

public class Result {
    String position, points, grid, givenName, familyName, name, time;

    public Result(String position, String points, String grid, String givenName, String familyName, String name, String time) {
        this.position = position;
        this.points = points;
        this.grid = grid;
        this.givenName = givenName;
        this.familyName = familyName;
        this.name = name;
        this.time = time;
    }

    public String getPosition() {
        return position;
    }
    public String getPoints() {
        return points;
    }
    public String getGrid() {
        return grid;
    }
    public String getGivenName() {
        return givenName;
    }
    public String getFamilyName() {
        return familyName;
    }
    public String getName() { return name; }
    public String getTime() {
        return time;
    }
}
