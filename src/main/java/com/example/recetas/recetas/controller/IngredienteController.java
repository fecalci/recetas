package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.IngredienteDto;
import com.example.recetas.recetas.dto.IngredienteFEDto;
import com.example.recetas.recetas.service.IngredienteService;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredienteController {

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping(value="/ingredient")
    public ResponseEntity<List<IngredienteFEDto>> getIngredients(){
        return ResponseEntity.ok().body(ingredienteService.getIngredients());
    }
}
