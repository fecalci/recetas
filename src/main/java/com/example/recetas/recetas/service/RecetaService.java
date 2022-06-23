package com.example.recetas.recetas.service;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaDtoSinMulti;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.dto.RecetaPorUsuarioDto;
import com.example.recetas.recetas.model.RecetaPorUsuario;

import java.util.List;

public interface RecetaService {

    List<RecetaDto> getRecetasByFilter(RecetaFilterDto filter);

    RecetaDtoSinMulti submitReceta(RecetaDtoSinMulti recetaDto);

    RecetaPorUsuario submitRecetaForLater(Long id, String alias);

    List<RecetaPorUsuarioDto> getRecetasForLater(String alias);

    String deleteRecetasForLater(Long id);

}
