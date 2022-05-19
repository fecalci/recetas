package com.example.recetas.recetas.dto;

public class SavedRecipeDto {

    private String user;
    private String nombre;
    private String url;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SavedRecipeDto(String user, String nombre, String url) {
        this.user = user;
        this.nombre = nombre;
        this.url = url;
    }
}
