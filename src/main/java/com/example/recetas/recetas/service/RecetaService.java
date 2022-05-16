package com.example.recetas.recetas.service;

import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.model.Receta;

import java.util.List;

public interface RecetaService {

    List<Receta> getRecetasByFilter(RecetaFilterDto filter);
}
