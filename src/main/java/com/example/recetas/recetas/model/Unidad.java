package com.example.recetas.recetas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Unidad {

    @Id
    @GeneratedValue
    private Long idUnidad;

    private String descrpicion;

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getDescrpicion() {
        return descrpicion;
    }

    public void setDescrpicion(String descrpicion) {
        this.descrpicion = descrpicion;
    }
}
