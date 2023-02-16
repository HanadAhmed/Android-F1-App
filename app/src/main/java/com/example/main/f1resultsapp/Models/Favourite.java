package com.example.main.f1resultsapp.Models;

public class Favourite {
    String permanentNumber, givenName, familyName, dateOfBirth, nationality,url;

    public Favourite(String permanentNumber, String givenName, String familyName, String dateOfBirth, String nationality, String url) {
        this.permanentNumber = permanentNumber;
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.url = url;
    }

    public String getPermanentNumber() { return permanentNumber;
    }

    public String getGivenName() { return givenName;
    }

    public String getFamilyName() { return familyName;
    }

    public String getDateOfBirth() { return dateOfBirth;
    }

    public String getNationality() { return nationality;
    }

    public String getUrl() { return url;
    }
}
