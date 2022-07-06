package com.example.recetas.recetas.dto;

import java.util.List;

public class PasoDtoSinMulti {

    private Long idPaso;

    private String descripcion;

    private String multimedia;

    public Long getIdPaso() {
        return idPaso;
    }

    public void setIdPaso(Long idPaso) {
        this.idPaso = idPaso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }
}
