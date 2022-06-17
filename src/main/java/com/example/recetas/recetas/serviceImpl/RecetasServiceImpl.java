package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.IngredienteDto;
import com.example.recetas.recetas.dto.PasoDto;
import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.model.*;
import com.example.recetas.recetas.repository.*;
import com.example.recetas.recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.*;
import static java.util.stream.Collectors.*;

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
        Receta receta = recetaRepository.findById(utilizado.getIdReceta()).orElse(null);
        RecetaDto dto = recetaToDto(receta, null);
        return dto;
    }


    public RecetaDto recetaToDto(Receta receta, RecetaFilterDto filter) {
        RecetaDto dto = new RecetaDto();
        List<PasoDto> pasosList = new ArrayList<>();
        List<IngredienteDto> ingredientes = new ArrayList<>();

        Optional<Tipo> tag = tipoRepository.findById(receta.getTag());
        dto.setTagString(String.valueOf(tag));
        dto.setReceta(receta);

        List<Utilizado> recetaUtilizado = utilizadoRepository.findByIdReceta(receta.getIdReceta());
        //Traigo ingrediente para cada Utilizado
        for (Utilizado utilizado : recetaUtilizado) {
            Optional<Ingrediente> ing = ingredienteRepository.findById(utilizado.getIdIngrediente());
            IngredienteDto ingDto = new IngredienteDto();
            //Valido con los filtros de ingredient y notIngredient
            if (filter.getNotIngredient() != null && !filter.getNotIngredient().contains(ing)) {
                if(filter.getIngredient() != null && filter.getIngredient().contains(ing)){
                    ing.ifPresent(i -> {
                            ingDto.setIdIngrediente(i.getIdIngrediente());
                            ingDto.setMedida(utilizado.getIdUnidad().getDescrpicion());
                            ingDto.setCantidad(utilizado.getCantidad());
                    });
                }
                else if(filter.getIngredient() == null){
                    ing.ifPresent(i -> {
                        ingDto.setIdIngrediente(i.getIdIngrediente());
                        ingDto.setMedida(utilizado.getIdUnidad().getDescrpicion());
                        ingDto.setCantidad(utilizado.getCantidad());
                    });
                }
            }
            ingredientes.add(ingDto);
        }
        dto.setIngredienteConCantidad(ingredientes);

        List<Paso> pasos = pasoRepository.findByIdReceta(receta.getIdReceta());

        for (Paso paso : pasos) {
            PasoDto pasoDto = new PasoDto();
            List<Multimedia> multimedia = multimediaRepository.findByIdPaso(paso.getIdPaso());

            pasoDto.setMultimedia(multimedia.stream()
                    .map(Multimedia::getUrlContenido).collect(toList()));
            pasoDto.setIdPaso(paso.getIdPaso());
            pasoDto.setDescripcion(paso.getTexto());
            pasosList.add(pasoDto);
        }
        dto.setPasos(pasosList);
        return dto;
    }

    @Override
    public RecetaDto submitReceta(RecetaDto recetaDto) {
        //Guardo los ingredientes con sus respectivas cantidades en utilizado
        for(IngredienteDto ingredienteDto : recetaDto.getIngredienteConCantidad()){
            Utilizado utilizado = new Utilizado();
            utilizado.setIdReceta(recetaDto.getReceta().getIdReceta());
            utilizado.setIdIngrediente(ingredienteDto.getIdIngrediente());
            utilizado.setCantidad(ingredienteDto.getCantidad());
            utilizadoRepository.save(utilizado);
        }

        Receta receta = new Receta();
        receta.setDescripcion(recetaDto.getReceta().getDescripcion());
        receta.setCantidadPersonas(recetaDto.getReceta().getCantidadPersonas());
        receta.setNombre(recetaDto.getReceta().getNombre());
        receta.setFoto(recetaDto.getReceta().getFoto());
        receta.setIdUsuario(recetaDto.getReceta().getIdUsuario());
        receta.setPorciones(recetaDto.getReceta().getPorciones());
        receta.setTag(recetaDto.getReceta().getTag());
        recetaRepository.save(recetaDto.getReceta());

        for(PasoDto paso : recetaDto.getPasos()){
            Paso newPaso = new Paso();
            newPaso.setNroPaso(toIntExact(paso.getIdPaso()));
            newPaso.setIdReceta(recetaDto.getReceta().getIdReceta());
            newPaso.setTexto(paso.getDescripcion());
            pasoRepository.save(newPaso);

            for(int i = 0; i<paso.getMultimedia().size(); i++){
                Multimedia multimedia = new Multimedia();
                multimedia.setUrlContenido(paso.getMultimedia().get(i));
                multimedia.setIdPaso(paso.getIdPaso());
                multimediaRepository.save(multimedia);
            }
        }
        return recetaDto;
    }
}