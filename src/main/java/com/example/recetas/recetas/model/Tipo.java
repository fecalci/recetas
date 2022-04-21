package com.example.recetas.recetas.model;

public class Tipo {
    private Long idTipo;
    private String descripcion;

    public Tipo() {
    }

    public Tipo(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo(Long idTipo, String descripcion) {
        this.idTipo = idTipo;
        this.descripcion = descripcion;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Tipo{" +
                "idTipo=" + idTipo +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
