package com.example.recetas.recetas.dto;

import org.springframework.web.bind.annotation.RequestParam;

public class RecetaFilterDto {

    private String name;
    private String type;
    private String Ingredient;
    private String user;
    private String notIngredient;

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

    public String getIngredient() {
        return Ingredient;
    }

    public void setIngredient(String ingredient) {
        Ingredient = ingredient;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNotIngredient() {
        return notIngredient;
    }

    public void setNotIngredient(String notIngredient) {
        this.notIngredient = notIngredient;
    }
}
