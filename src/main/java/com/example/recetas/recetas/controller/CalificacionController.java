package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.model.Calificacion;
import com.example.recetas.recetas.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalificacionController {

    @Autowired
    private CalificacionService calificacionService;

    @PostMapping(value="calificacion/{nickname}/{number}/{recipeId}")
    public ResponseEntity<Calificacion> submitCalification(@PathVariable("nickname") String nickname,
                                                           @PathVariable("number") Long number,
                                                           @PathVariable("recipeId") Long recipeId){

        return ResponseEntity.ok().body(calificacionService.submitCalificacion(nickname,number,recipeId));
    }
}
