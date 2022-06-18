package com.example.recetas.recetas.model;

import javax.persistence.*;

@Entity
@Table
public class Utilizado {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long idUtilizado;

    private Long idReceta;

    private Long idIngrediente;

    private int cantidad;

    private Long idUnidad;

    private String observaciones;

    public long getIdUtilizado() {
        return idUtilizado;
    }

    public void setIdUtilizado(long idUtilizado) {
        this.idUtilizado = idUtilizado;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Utilizado() {
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Utilizado(long idUtilizado, Long idReceta, Long idIngrediente, int cantidad, Long idUnidad, String observaciones) {
        this.idUtilizado = idUtilizado;
        this.idReceta = idReceta;
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
        this.idUnidad = idUnidad;
        this.observaciones = observaciones;
    }
}
