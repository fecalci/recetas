package com.example.recetas.recetas.dto;

import com.example.recetas.recetas.model.Ingrediente;
import com.example.recetas.recetas.model.Tipo;

import java.util.List;

public class RecetaDto {

    private Long idUser;
    private String name;
    private String image;
    private int porciones;
    private String description;
    private List<Ingrediente> ingredients;
    private List<Tipo> tag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingrediente> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingrediente> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Tipo> getTag() {
        return tag;
    }

    public void setTag(List<Tipo> tag) {
        this.tag = tag;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setUser(Long idUser) {
        this.idUser = idUser;
    }
}
