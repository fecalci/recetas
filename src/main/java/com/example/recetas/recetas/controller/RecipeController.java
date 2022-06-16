package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.dto.SavedRecipeDto;
import com.example.recetas.recetas.model.Receta;
import com.example.recetas.recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
public class RecipeController {

    @Autowired
    RecetaService recetaService;

    @GetMapping(value = "recipe")
    public ResponseEntity<List<RecetaDto>> getRecipes(@RequestParam String name, @RequestParam String type,
                                                  @RequestParam List<Long> ingredients, @RequestParam String user,
                                                  @RequestParam List<Long> notIngredients) {

        RecetaFilterDto filter = new RecetaFilterDto(name,type,user, ingredients, notIngredients);

        return ResponseEntity.ok().body(recetaService.getRecetasByFilter(filter));
    }

    @PostMapping(value="recipe")
    public RecetaDto submitRecipe(@RequestBody RecetaDto receta){
        return receta;
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

    @GetMapping(value="/myRecipesForLater")
    public List<SavedRecipeDto> getRecipesForLater(){
        SavedRecipeDto receta1 = new SavedRecipeDto
                ("Camila93","Guacamole Mexicano","guacamole.url");
        SavedRecipeDto receta2 = new SavedRecipeDto
                ("Leonardo55","Carne al Horno","carne.url");
        List <SavedRecipeDto> myList = new ArrayList<>();
        myList.add(receta1);
        myList.add(receta2);
        return myList;

    }

}
