package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaDtoSinMulti;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.dto.RecetaPorUsuarioDto;
import com.example.recetas.recetas.exception.ApiException;
import com.example.recetas.recetas.exception.ApiRequestException;
import com.example.recetas.recetas.model.RecetaPorUsuario;
import com.example.recetas.recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RecipeController {

    @Autowired
    RecetaService recetaService;


    @GetMapping(value = "recipe")
    public ResponseEntity<List<RecetaDto>> getRecipes(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String type,
                                                      @RequestParam(required = false) List<String> ingredients,
                                                      @RequestParam(required = false) String user,
                                                      @RequestParam(required = false) List<String> notIngredients) {

        RecetaFilterDto filter = new RecetaFilterDto(name,type,user, ingredients, notIngredients);

        return ResponseEntity.ok().body(recetaService.getRecetasByFilter(filter));
    }

    @PostMapping(value="recipe")
    public ResponseEntity<RecetaDtoSinMulti> submitRecipe(@RequestBody RecetaDtoSinMulti receta){
        return ResponseEntity.ok().body(recetaService.submitReceta(receta));
    }

    @PostMapping(value="/recipeForLater/{id}/{nickname}")
    public ResponseEntity<Object> getRecipesForLater(@PathVariable("id") Long recipeId,
                                                               @PathVariable("nickname") String nickname) throws Exception, ApiException {
        RecetaPorUsuario recetaPorUsuario = new RecetaPorUsuario();
        try{
            recetaPorUsuario = recetaService.submitRecetaForLater(recipeId, nickname);
        }
        catch (Exception ex){
            throw ex;
        } catch (ApiException e) {
            throw e;
        }
        return ResponseEntity.ok().body(recetaPorUsuario);
    }

    @GetMapping(value="/recipeForLater/{nickname}")
    public ResponseEntity<List<RecetaPorUsuarioDto>> getRecipesForLaterByUser(@PathVariable("nickname") String nickname){
        return ResponseEntity.ok().body(recetaService.getRecetasForLater(nickname));
    }

    @DeleteMapping(value="/recipeForLater/{id}")
    public ResponseEntity<String> deteleRecipesForLater(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(recetaService.deleteRecetasForLater(id));
    }

}
