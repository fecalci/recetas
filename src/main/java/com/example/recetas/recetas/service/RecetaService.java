package com.example.recetas.recetas.service;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaFilterDto;

import java.util.List;

public interface RecetaService {

    List<RecetaDto> getRecetasByFilter(RecetaFilterDto filter);

    RecetaDto submitReceta(RecetaDto recetaDto);
}
