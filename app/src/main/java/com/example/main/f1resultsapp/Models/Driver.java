package com.example.main.f1resultsapp.Models;

public class Driver {
    String position, points, wins, givenName, familyName, name, id;

    public Driver(String position, String points, String wins, String givenName, String familyName, String name, String id) {
        this.position = position;
        this.points = points;
        this.wins = wins;
        this.givenName = givenName;
        this.familyName = familyName;
        this.name = name;
        this.id = id;
    }

    public String getPosition() { return position;
    }

    public String getPoints() { return points;
    }

    public String getWins() { return wins;
    }

    public String getGivenName() { return givenName;
    }

    public String getFamilyName() { return familyName;
    }

    public String getName() { return name;
    }

    public String getId() { return id;
    }
}
