package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.IngredienteDto;
import com.example.recetas.recetas.dto.IngredienteFEDto;
import com.example.recetas.recetas.model.Ingrediente;
import com.example.recetas.recetas.repository.IngredienteRepository;
import com.example.recetas.recetas.service.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredienteServiceImpl implements IngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public List<IngredienteFEDto> getIngredients(){
        List<IngredienteFEDto> responseDto = new ArrayList<>();
        List<Ingrediente> ingredientes = ingredienteRepository.findAll();
        for(Ingrediente ingrediente : ingredientes){
            IngredienteFEDto ing = new IngredienteFEDto();
            ing.setIdIngrediente(ingrediente.getIdIngrediente());
            ing.setNombre(ingrediente.getNombre());
            ing.setBackgroundColor("white");
            responseDto.add(ing);
        }
        return responseDto;
    }
}
