package com.example.recetas.recetas.dto;

import com.example.recetas.recetas.model.Receta;

import java.util.List;

public class RecetaDtoSinMulti {

    private Receta receta;

    private String creatorNickname;

    private List<IngredienteDto> ingredienteConCantidad;

    private List<PasoDtoSinMulti> pasos;

    private String tagString;

    private int calificacion;

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }

    public List<IngredienteDto> getIngredienteConCantidad() {
        return ingredienteConCantidad;
    }

    public void setIngredienteConCantidad(List<IngredienteDto> ingredienteConCantidad) {
        this.ingredienteConCantidad = ingredienteConCantidad;
    }

    public List<PasoDtoSinMulti> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoDtoSinMulti> pasos) {
        this.pasos = pasos;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public RecetaDtoSinMulti() {
    }
}
