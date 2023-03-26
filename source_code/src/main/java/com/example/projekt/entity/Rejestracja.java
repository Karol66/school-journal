package com.example.projekt.entity;

import jakarta.persistence.*;

@Entity
@Table(name="rejestracja")
public class Rejestracja {

    @Id
    @Column(name = "id_rejestracja")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //jeśli id jest autoinkrement
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "haslo")
    private String haslo;

    public Rejestracja(int id, String email, String login, String haslo) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.haslo = haslo;
    }

    public Rejestracja() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String nazwa) {
        this.login = nazwa;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    @Override
    public String toString() {
        return "Rejestracja{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nazwa='" + login + '\'' +
                ", haslo='" + haslo + '\'' +
                '}';
    }
}
