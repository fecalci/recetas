package com.example.recetas.recetas.model;

import javax.persistence.*;

@Entity
@Table
public class Paso {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idPaso;

    @ManyToOne
    @JoinColumn(name ="idReceta")
    private Receta idReceta;

    private int nroPaso;
    private String texto;

    public Long getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(Long idPaso) {
        this.idPaso = idPaso;
    }

    public Receta getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Receta idReceta) {
        this.idReceta = idReceta;
    }

    public int getNroPaso() {
        return nroPaso;
    }

    public void setNroPaso(int nroPaso) {
        this.nroPaso = nroPaso;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Paso(Long idPaso, Receta idReceta, int nroPaso, String texto) {
        this.idPaso = idPaso;
        this.idReceta = idReceta;
        this.nroPaso = nroPaso;
        this.texto = texto;
    }


}
