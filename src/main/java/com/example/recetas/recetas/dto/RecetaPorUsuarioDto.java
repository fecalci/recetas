package com.example.recetas.recetas.dto;

import com.example.recetas.recetas.model.Receta;

public class RecetaPorUsuarioDto {

    private String nickName;

    private Receta receta;


    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public RecetaPorUsuarioDto() {
    }
}
