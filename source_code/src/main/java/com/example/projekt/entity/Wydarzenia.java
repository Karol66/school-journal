package com.example.projekt.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "wydarzenia")
public class Wydarzenia {

    @Id
    @Column(name = "id_wydarzenia")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //jeśli id jest autoinkrement
    private int id;

    @Column(name = "wydarzenie")
    private String wydarzenie;

    @Column(name = "data")
    private LocalDate data;

    public Wydarzenia(int id, String wydarzenie, LocalDate data) {
        this.id = id;
        this.wydarzenie = wydarzenie;
        this.data = data;
    }

    public Wydarzenia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWydarzenie() {
        return wydarzenie;
    }

    public void setWydarzenie(String wydarzenie) {
        this.wydarzenie = wydarzenie;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Wydarzenia{" +
                "id=" + id +
                ", wydarzenie='" + wydarzenie + '\'' +
                ", data=" + data +
                '}';
    }
}
