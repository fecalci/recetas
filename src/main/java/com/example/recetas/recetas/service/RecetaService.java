package com.example.recetas.recetas.service;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaDtoSinMulti;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.dto.RecetaPorUsuarioDto;
import com.example.recetas.recetas.exception.ApiException;
import com.example.recetas.recetas.model.Receta;
import com.example.recetas.recetas.model.RecetaPorUsuario;

import java.util.List;

public interface RecetaService {

    List<RecetaDto> getRecetasByFilter(RecetaFilterDto filter);

    RecetaDtoSinMulti submitReceta(RecetaDtoSinMulti recetaDto);

    RecetaPorUsuario submitRecetaForLater(Long id, String alias) throws Exception, ApiException;

    List<RecetaPorUsuarioDto> getRecetasForLater(String alias);

    String deleteRecetasForLater(Long id);

    List<RecetaDto> bestRecipes();

    RecetaDto existRecipe(String alias, String recipeName);
}
