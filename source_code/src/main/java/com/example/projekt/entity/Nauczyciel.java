package com.example.projekt.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "nauczyciel")
public class Nauczyciel {

    @Id
    @Column(name = "index_nauczyciela")
    private int id;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "plec")
    private String plec;

    @Column(name = "data_urodzenia")
    private LocalDate data_urodzenia;

    @Column(name = "prowadzony_przedmiot")
    private String prowadzony_przedmiot;

    @Column(name = "wyplata")
    private double wyplata;

    @Column(name = "zdjecie")
    private String zdjecie;

    public Nauczyciel(int id, String imie, String nazwisko, String plec, LocalDate data_urodzenia, String prowadzony_przedmiot, double wyplata, String zdjecie) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.plec = plec;
        this.data_urodzenia = data_urodzenia;
        this.prowadzony_przedmiot = prowadzony_przedmiot;
        this.wyplata = wyplata;
        this.zdjecie = zdjecie;
    }

    public Nauczyciel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public LocalDate getData_urodzenia() {
        return data_urodzenia;
    }

    public void setData_urodzenia(LocalDate data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public String getProwadzony_przedmiot() {
        return prowadzony_przedmiot;
    }

    public void setProwadzony_przedmiot(String prowadzony_przedmiot) {
        this.prowadzony_przedmiot = prowadzony_przedmiot;
    }

    public double getWyplata() {
        return wyplata;
    }

    public void setWyplata(double wyplata) {
        this.wyplata = wyplata;
    }

    public String getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }

    @Override
    public String toString() {
        return "Nauczyciel{" +
                "id=" + id +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", plec='" + plec + '\'' +
                ", data_urodzenia=" + data_urodzenia +
                ", prowadzony_przedmiot='" + prowadzony_przedmiot + '\'' +
                ", wyplata=" + wyplata +
                ", zdjecie='" + zdjecie + '\'' +
                '}';
    }
}
