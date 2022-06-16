package com.example.recetas.recetas.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class RecetaFilterDto {

    private String name;
    private String type;
    private String user;

    private List<String> Ingredient;
    private List<String> notIngredient;

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

    public List<String> getIngredient() {
        return Ingredient;
    }

    public void setIngredient(List<String> ingredient) {
        Ingredient = ingredient;
    }

    public List<String> getNotIngredient() {
        return notIngredient;
    }

    public void setNotIngredient(List<String> notIngredient) {
        this.notIngredient = notIngredient;
    }
}
