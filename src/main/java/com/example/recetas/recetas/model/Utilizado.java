package com.example.recetas.recetas.model;

import javax.persistence.*;

@Entity
@Table
public class Utilizado {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long idUtilizado;

    @ManyToOne
    @JoinColumn(name="idReceta")
    private Receta idReceta;

    private Long idIngrediente;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name="idUnidad")
    private Unidad idUnidad;

    private String observaciones;

    public Receta getIdReceta() {
        return idReceta;
    }

    public long getIdUtilizado() {
        return idUtilizado;
    }

    public void setIdUtilizado(long idUtilizado) {
        this.idUtilizado = idUtilizado;
    }

    public void setIdReceta(Receta idReceta) {
        this.idReceta = idReceta;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Unidad getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Unidad idUnidad) {
        this.idUnidad = idUnidad;
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

    public Utilizado(long idUtilizado, Receta idReceta, Long idIngrediente, int cantidad, Unidad idUnidad, String observaciones) {
        this.idUtilizado = idUtilizado;
        this.idReceta = idReceta;
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
        this.idUnidad = idUnidad;
        this.observaciones = observaciones;
    }
}
