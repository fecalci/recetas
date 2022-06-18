package com.example.recetas.recetas.dto;

import com.example.recetas.recetas.model.*;
import java.util.List;

public class RecetaDto {

    private Receta receta;

    private String creatorNickname;

    private List<IngredienteDto> ingredienteConCantidad;

    private List<PasoDto> pasos;

    private String tagString;

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

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

    public List<PasoDto> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoDto> pasos) {
        this.pasos = pasos;
    }


    public List<IngredienteDto> getIngredienteConCantidad() {
        return ingredienteConCantidad;
    }

    public void setIngredienteConCantidad(List<IngredienteDto> ingredienteConCantidad) {
        this.ingredienteConCantidad = ingredienteConCantidad;
    }
}
