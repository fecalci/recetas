package com.example.recetas.recetas.dto;

import com.example.recetas.recetas.model.Ingrediente;

public class IngredienteFEDto {

    private Long idIngrediente;
    private String nombre;
    private String backgroundColor;

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public IngredienteFEDto() {
    }
}
