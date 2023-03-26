package com.example.projekt.entity;

import java.util.ArrayList;
import java.util.List;

public class FakeFrekwencja {

    private int idUcznia;
    private String imie;
    private String nazwisko;
    private List<Double> frekwencja = new ArrayList<>();

    public FakeFrekwencja() {
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

    public FakeFrekwencja(int idUcznia, String imie, String nazwisko, List<Double> frekwencja) {
        this.idUcznia = idUcznia;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.frekwencja = frekwencja;
    }


    public void dodajFrekwencje(double frekwencja) {
        this.frekwencja.add(frekwencja);
    }

    public String getFrekwencje(int iterator) {
        if (iterator >= this.frekwencja.size()) {
            return "";
        }
        return Double.toString(this.frekwencja.get(iterator));
    }

    public List<Double> getFrekwencja() {
        return frekwencja;
    }

    public void setFrekwencja(List<Double> frekwencja) {
        this.frekwencja = frekwencja;
    }

    @Override
    public String toString() {
        return "FakeFrekwencja{" +
                "idUcznia=" + idUcznia +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", frekwencja=" + frekwencja +
                '}';
    }
}
