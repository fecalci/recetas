package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.dto.RecetaPorUsuarioDto;
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
    public ResponseEntity<RecetaDto> submitRecipe(@RequestBody RecetaDto receta){
        return ResponseEntity.ok().body(recetaService.submitReceta(receta));
    }

    /*
    @DeleteMapping(value="recipe")
    public Receta replaceRecipe(@RequestBody RecetaDto receta, String idReceta){
    }
     */

    /*
    @PutMapping(value="recipe")
    public Receta editRecipe(@RequestBody RecetaDto receta, String idReceta){
    }

     */

    @PostMapping(value="/save/{id}")
    public String saveRecipeForLater(@PathVariable("id") Long recipeId){
        return ("Receta de id " + recipeId + " guardada con éxito!");
    }

    @DeleteMapping(value="/delete/{id}")
    public String deleteRecipeForLater(@PathVariable("id") Long recipeId){
        return ("Receta de id " + recipeId + " eliminada con éxito!");
    }

    @PutMapping(value="/qualify/{id}")
    public String qualifyRecipe(@PathVariable("id") Long recipeId, boolean like){
        return("Ahora te gusta la receta con id " + recipeId);
    }

    @PostMapping(value="/recipeForLater/{id}/{nickname}")
    public ResponseEntity<RecetaPorUsuario> getRecipesForLater(@PathVariable("id") Long recipeId,
                                                               @PathVariable("nickname") String nickname){
        return ResponseEntity.ok().body(recetaService.submitRecetaForLater(recipeId, nickname));
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
