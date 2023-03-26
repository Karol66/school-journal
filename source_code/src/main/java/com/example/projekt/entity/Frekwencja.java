package com.example.projekt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "frekwencja")
public class Frekwencja {

    @Id
    @Column(name = "id_frekwencja")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //jeśli id jest autoinkrement
    private int id_frekwencja;

    @Column(name = "frekwencja")
    private String frekwencja;

    @ManyToOne(targetEntity = Uczen.class)
    @JoinColumn(name = "index_ucznia")
    private Uczen index_ucznia;

    public Frekwencja(String frekwencja, Uczen index_ucznia) {
        this.id_frekwencja = id_frekwencja;
        this.frekwencja = frekwencja;
        this.index_ucznia = index_ucznia;
    }

    public Frekwencja() {
    }

    public int getId_frekwencja() {
        return id_frekwencja;
    }

    public void setId_frekwencja(int id_frekwencja) {
        this.id_frekwencja = id_frekwencja;
    }

    public String getFrekwencja() {
        return frekwencja;
    }

    public void setFrekwencja(String frekwencja) {
        this.frekwencja = frekwencja;
    }

    public Uczen getIndex_ucznia() {
        return index_ucznia;
    }

    public void setIndex_ucznia(Uczen index_ucznia) {
        this.index_ucznia = index_ucznia;
    }

    @Override
    public String toString() {
        return "Frekwencja{" +
                "id_frekwencja=" + id_frekwencja +
                ", frekwencja='" + frekwencja + '\'' +
                ", index_ucznia=" + index_ucznia +
                '}';
    }
}
