package com.example.projekt.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "oceny")
public class Oceny {

    @Id
    @Column(name = "id_oceny")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //jeśli id jest autoinkrement
    private int id_oceny;

    @Column(name = "ocena")
    private String ocena;

    @ManyToOne(targetEntity = Uczen.class)
    @JoinColumn(name = "index_ucznia")
    private Uczen index_ucznia;
    @ManyToOne(targetEntity = Lekcje.class)
    @JoinColumn(name = "id_lekcje")
    private Lekcje id_lekcje;


    public Oceny(String ocena, Uczen index_ucznia, Lekcje id_lekcje) {
        this.id_oceny = id_oceny;
        this.ocena = ocena;
        this.index_ucznia = index_ucznia;
        this.id_lekcje = id_lekcje;
    }

    public Oceny() {
    }

    public int getId_oceny() {
        return id_oceny;
    }

    public void setId_oceny(int id_oceny) {
        this.id_oceny = id_oceny;
    }

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }

    public Uczen getIndex_ucznia() {
        return index_ucznia;
    }

    public void setIndex_ucznia(Uczen index_ucznia) {
        this.index_ucznia = index_ucznia;
    }

    public Lekcje getId_lekcje() {
        return id_lekcje;
    }

    public void setId_lekcje(Lekcje id_lekcje) {
        this.id_lekcje = id_lekcje;
    }

    @Override
    public String toString() {
        return "Oceny{" +
                "id_oceny=" + id_oceny +
                ", ocena=" + ocena +
                ", index_ucznia=" + index_ucznia +
                ", id_lekcje=" + id_lekcje +
                '}';
    }
}

