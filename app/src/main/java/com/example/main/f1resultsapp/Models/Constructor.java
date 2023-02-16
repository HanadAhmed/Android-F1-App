package com.example.main.f1resultsapp.Models;

public class Constructor {
    String position, points, wins, name, nationality;

    public Constructor(String position, String points, String wins, String name, String nationality) {
        this.position = position;
        this.points = points;
        this.wins = wins;
        this.name = name;
        this.nationality = nationality;
    }

    public String getPosition() { return position;
    }

    public String getPoints() { return points;
    }

    public String getWins() { return wins;
    }

    public String getName() { return name;
    }

    public String getNationality() { return nationality;
    }
}
