package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.model.Receta;
import com.example.recetas.recetas.model.Tipo;
import com.example.recetas.recetas.model.Utilizado;
import com.example.recetas.recetas.repository.RecetaRepository;
import com.example.recetas.recetas.repository.TipoRepository;
import com.example.recetas.recetas.repository.UtilizadoRepository;
import com.example.recetas.recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecetasServiceImpl implements RecetaService {

    @Autowired
    RecetaRepository recetaRepository;

    @Autowired
    UtilizadoRepository utilizadoRepository;

    @Autowired
    TipoRepository tipoRepository;


    @Override
    public List<Receta> getRecetasByFilter(RecetaFilterDto filter) {

        List<Receta> responseRecetas = new ArrayList<>();
        List<Receta> recetas = recetaRepository.findByNombreOrIdUsuarioOrTag
                ("2",Long.valueOf(2),Long.valueOf(2));

        if(!recetas.isEmpty()){
            responseRecetas.addAll(recetas);
        }
        List<Utilizado> utilizados =utilizadoRepository.
                findByIngredients(filter.getIngredient(),filter.getNotIngredient());

        if(!utilizados.isEmpty()){
            for(Utilizado utilizado : utilizados){
                Optional<Receta> receta = recetaRepository.findById(utilizado.getIdReceta().getIdReceta());
                receta.ifPresent(responseRecetas::add);
            }
        }

        return responseRecetas;

    }
}
