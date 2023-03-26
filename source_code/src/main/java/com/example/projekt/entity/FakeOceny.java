package com.example.projekt.entity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeOceny {
    private int idUcznia;
    private String imie;
    private String nazwisko;
    private List<Double> oceny = new ArrayList<>();

    public FakeOceny() {
    }


    public int getIdUcznia() {
        return idUcznia;
    }

    public void setIdUcznia(int idUcznia) {
        this.idUcznia = idUcznia;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public FakeOceny(int idUcznia, String imie, String nazwisko, List<Double> oceny) {
        this.idUcznia = idUcznia;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.oceny = oceny;
    }

    public void dodajOcene(double ocena) {
        this.oceny.add(ocena);
    }

    public String getOcena(int iterator) {
        if (iterator >= this.oceny.size()) {
            return "";
        }
        return Double.toString(this.oceny.get(iterator));
    }

    public String getSrednia() {
        double srednia = 0.0;
        double x = 0;
        for (int i = 0; i < this.oceny.size(); i++) {
            srednia += oceny.get(i);
            x++;
        }
        if (x != 0)
        {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(srednia / x);
        }
        return "";
    }

    public List<Double> getOceny() {
        return oceny;
    }

    public void setOceny(List<Double> oceny) {
        this.oceny = oceny;
    }

    @Override
    public String toString() {
        return "FakeOceny{" +
                "idUcznia=" + idUcznia +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", oceny=" + oceny +
                '}';
    }
}


