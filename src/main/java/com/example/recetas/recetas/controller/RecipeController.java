package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.model.Ingrediente;
import com.example.recetas.recetas.model.Receta;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController(value="recipe")
public class RecipeController {

    @GetMapping(value="recipe")
    public List<Receta> getRecipes(@RequestParam String name, @RequestParam String type,
                                   @RequestParam List<Ingrediente> ingredients, @RequestParam String user,
                                   @RequestParam List<String> notIngredients){
        List<Receta> recipes = new ArrayList<>();
        return recipes;
    }

    @PostMapping(value="recipe")
    public Receta submitRecipe(@RequestBody RecetaDto receta){

        return new Receta(receta.getIdUser(),receta.getName(),
                receta.getDescription(),receta.getImage(), receta.getPorciones(),
                receta.getTag(), receta.getIngredients(), receta.getPreparation() );
    }

    @DeleteMapping(value="recipe")
    public Receta replaceRecipe(@RequestBody RecetaDto receta, String idReceta){
        return new Receta(receta.getIdUser(),receta.getName(),
                receta.getDescription(),receta.getImage(), receta.getPorciones(),
                receta.getTag(), receta.getIngredients(), receta.getPreparation() );
    }

    @PutMapping(value="recipe")
    public Receta editRecipe(@RequestBody RecetaDto receta, String idReceta){

        return new Receta(receta.getIdUser(),receta.getName(),
                receta.getDescription(),receta.getImage(), receta.getPorciones(),
                receta.getTag(), receta.getIngredients(), receta.getPreparation() );
    }

    @PostMapping(value="/save/{id}")
    public String saveRecipeForLater(@PathVariable("id") Long recipeId){
        return ("Receta de id " + recipeId + " guardada con éxito!");
    }

    @DeleteMapping(value="/delete/{id}")
    public String deleteRecipeForLater(@PathVariable("id") Long recipeId){
        return ("Receta de id " + recipeId + " eliminada con éxito!");
    }

    @PutMapping(value="/qualify/{id}")
    public String qualifyRecipe(@PathVariable("id") Long recipeId, int value){
        return("Receta de id "+ recipeId + "calificada con "+ value + " con éxtio");
    }

}
