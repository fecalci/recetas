package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.model.*;
import com.example.recetas.recetas.repository.*;
import com.example.recetas.recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecetasServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private UtilizadoRepository utilizadoRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private PasoRepository pasoRepository;

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<RecetaDto> getRecetasByFilter(RecetaFilterDto filter) {

        List<RecetaDto> recetaDtos = new ArrayList<>();

        List<Receta> recetas = recetaRepository.findByNombreOrIdUsuarioOrTag
                (filter.getName(), filter.getUser() != null ?
                                userRepository.findByAlias(filter.getUser()).getId() : null,
                        filter.getType() != null ?
                                tipoRepository.findByDescripcion(filter.getType()).getIdTipo() : null);

        if (!recetas.isEmpty()) {
            for (Receta receta : recetas) {
                RecetaDto dto = recetaToDto(receta, filter);
                recetaDtos.add(dto);
            }
        }

        else {
            List<Long> notIngredientsIds = null;
            List<Long> ingredientsIds = null;

            if (filter.getIngredient() != null) {
                ingredientsIds = new ArrayList<>();
                for (String nombre : filter.getIngredient()) {
                    ingredientsIds.add(ingredienteRepository.findByNombre(nombre).getIdIngrediente());
                }
            }
            if (filter.getNotIngredient() != null) {
                notIngredientsIds = new ArrayList<>();
                for (String nombre : filter.getNotIngredient()) {
                    notIngredientsIds.add(ingredienteRepository.findByNombre(nombre).getIdIngrediente());
                }
            }
            List<Utilizado> utilizados = utilizadoRepository.findByIngredients
                    (ingredientsIds, notIngredientsIds);

            if (!utilizados.isEmpty()) {
                for (Utilizado utilizado : utilizados) {
                    RecetaDto dto = utilizadoToRecetaDto(utilizado);
                    recetaDtos.add(dto);
                }
            }

            return recetaDtos;

        }

        return recetaDtos;
    }

    private RecetaDto utilizadoToRecetaDto(Utilizado utilizado) {
        Receta receta = recetaRepository.findById(utilizado.getIdReceta().getIdReceta()).orElse(null);
        RecetaDto dto = recetaToDto(receta, null);
        return null;
    }


    public RecetaDto recetaToDto(Receta receta, RecetaFilterDto filter) {
        List<String> ingredientes = new ArrayList<>();
        RecetaDto dto = new RecetaDto();
        dto.setReceta(receta);

        List<Utilizado> recetaUtilizado = utilizadoRepository.findByRecetaId(receta.getIdReceta());
        //Traigo ingrediente para cada Utilizado
        for (Utilizado utilizado : recetaUtilizado) {

            Optional<Ingrediente> ing = ingredienteRepository.findById(utilizado.getIdIngrediente());
            //Valido con los filtros de ingredient y notIngredient
            if (filter.getNotIngredient() != null && !filter.getNotIngredient().contains(ing)) {
                if(filter.getIngredient() != null && filter.getIngredient().contains(ing)){
                    ing.ifPresent(ingrediente -> ingredientes.add(ingrediente.getNombre()));
                }
                else if(filter.getIngredient() == null){
                    ing.ifPresent(ingrediente -> ingredientes.add(ingrediente.getNombre()));
                }
            }
        }

        List<Paso> pasos = pasoRepository.findByRecetaId(receta.getIdReceta());
        dto.setPasos(pasoRepository.findByRecetaId(receta.getIdReceta()));
        //Traigo listado de multimedas
        for (Paso paso : pasos) {
            List<Multimedia> multimedia = multimediaRepository.findByIdPaso(paso.getIdPaso());
            dto.setMultimedia(multimedia);
        }
        return dto;
    }
}