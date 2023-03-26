package com.example.projekt.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "uczen")
public class Uczen {

    @Id
    @Column(name = "index_ucznia")
    private int id;

    @Column(name = "rok")
    private String rok;

    @Column(name = "profil")
    private String profil;

    @Column(name = "imie")
    private String imie;

    @Column(name = "nazwisko")
    private String nazwisko;

    @Column(name = "plec")
    private String plec;

    @Column(name = "data_urodzenia")
    private LocalDate data_urodzenia;

    @Column(name = "zdjecie")
    private String zdjecie;

    @OneToMany(mappedBy = "index_ucznia", targetEntity=Oceny.class)
    private List<Oceny> oceny;

    public List<Oceny> getOceny() {
        return oceny;
    }

    @OneToMany(mappedBy = "index_ucznia", targetEntity=Frekwencja.class)
    private List<Frekwencja> frekwencja;

    public List<Frekwencja> getFrekwencja() {
        return frekwencja;
    }

    public Uczen(int id, String rok, String profil, String imie, String nazwisko, String plec, LocalDate data_urodzenia, String zdjecie) {
        this.id = id;
        this.rok = rok;
        this.profil = profil;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.plec = plec;
        this.data_urodzenia = data_urodzenia;
        this.zdjecie = zdjecie;
    }

    public Uczen() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getImie() {
        return imie;
    }

    public String getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
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

//    public Set<Oceny1> getOceny() {
//        return oceny;
//    }
//
//    public void setOceny(Set<Oceny1> oceny) {
//        this.oceny = oceny;
//    }

    @Override
    public String toString() {
        return "Uczen{" +
                "id=" + id +
                ", rok=" + rok +
                ", profil='" + profil + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", plec='" + plec + '\'' +
                ", data_urodzenia=" + data_urodzenia +
                ", zdjecie='" + zdjecie + '\'' +
                '}';
    }
}
