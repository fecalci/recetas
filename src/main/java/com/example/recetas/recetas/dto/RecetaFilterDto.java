package com.example.recetas.recetas.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class RecetaFilterDto {

    private String name;
    private String type;
    private String user;

    private List<Long> Ingredient;
    private List<Long> notIngredient;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Long> getIngredient() {
        return Ingredient;
    }

    public void setIngredient(List<Long> ingredient) {
        Ingredient = ingredient;
    }

    public List<Long> getNotIngredient() {
        return notIngredient;
    }

    public void setNotIngredient(List<Long> notIngredient) {
        this.notIngredient = notIngredient;
    }

    public RecetaFilterDto(String name, String type, String user, List<Long> ingredient, List<Long> notIngredient) {
        this.name = name;
        this.type = type;
        this.user = user;
        Ingredient = ingredient;
        this.notIngredient = notIngredient;
    }
}
