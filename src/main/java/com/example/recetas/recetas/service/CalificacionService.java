package com.example.recetas.recetas.service;

import com.example.recetas.recetas.model.Calificacion;

import java.util.List;

public interface CalificacionService {

    Calificacion submitCalificacion(String nickname,Long number, Long recipeId);

    List<Calificacion> findByIdReceta(Long recipeId);

    int getAverageValueByReceta(List<Calificacion> calificaciones);

}
