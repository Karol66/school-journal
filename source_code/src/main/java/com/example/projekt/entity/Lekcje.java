package com.example.projekt.entity;

import jakarta.persistence.*;

@Entity
@Table(name="lekcje")
public class Lekcje {

    @Id
    @Column(name = "id_lekcje")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //jeśli id jest autoinkrement
    private int id;

    @Column(name = "nazwa_lekcji")
    private String nazwa_lekcji;

    public Lekcje(int id, String nazwa_lekcji) {
        this.id = id;
        this.nazwa_lekcji = nazwa_lekcji;
    }

    public Lekcje() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa_lekcji() {
        return nazwa_lekcji;
    }

    public void setNazwa_lekcji(String nazwa_lekcji) {
        this.nazwa_lekcji = nazwa_lekcji;
    }

    @Override
    public String toString() {
        return "Lekcje{" +
                "id=" + id +
                ", nazwa_lekcji='" + nazwa_lekcji + '\'' +
                '}';
    }
}
