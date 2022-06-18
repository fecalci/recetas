package com.example.recetas.recetas.model;

import javax.persistence.*;

@Entity
@Table
public class Paso {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idPaso;

    private Long idReceta;

    private int nroPaso;
    private String texto;

    public Paso() {

    }

    public Long getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(Long idPaso) {
        this.idPaso = idPaso;
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

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Paso(Long idPaso, Long idReceta, int nroPaso, String texto) {
        this.idPaso = idPaso;
        this.idReceta = idReceta;
        this.nroPaso = nroPaso;
        this.texto = texto;
    }
}
