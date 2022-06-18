package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.IngredienteDto;
import com.example.recetas.recetas.dto.PasoDto;
import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.model.*;
import com.example.recetas.recetas.repository.*;
import com.example.recetas.recetas.service.RecetaService;
import jdk.jshell.execution.Util;
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

    @Autowired
    private UnidadRepository unidadRepository;


    @Override
    public List<RecetaDto> getRecetasByFilter(RecetaFilterDto filter) {

        List<RecetaDto> recetaDtos = new ArrayList<>();
        List<Long> ingredientsId = new ArrayList<>();
        List<Long> notIngredientsId = new ArrayList<>();
        //Aplico filtros de nombre, id usuario o tag
        if(filter.getIngredient() != null){
            for(String nombre : filter.getIngredient()){
                ingredientsId.add(ingredienteRepository.findByNombre(nombre).getIdIngrediente());
            }
        }

        if(filter.getNotIngredient() != null){
            for(String nombre : filter.getNotIngredient()){
                notIngredientsId.add(ingredienteRepository.findByNombre(nombre).getIdIngrediente());
            }
        }

        List<Receta> recetas  = recetaRepository.findRecetasByFilter(filter.getName() != null? filter.getName().toLowerCase() : null, filter.getUser() != null ?
                        userRepository.findByAlias(filter.getUser()).getId() : null,
                filter.getType() != null ? tipoRepository.findByDescripcion(filter.getType()).getIdTipo() : null,
                ingredientsId, notIngredientsId);

/*
        List<Receta> recetas = recetaRepository.findByNombreOrIdUsuarioOrTag
                (filter.getName() != null? filter.getName().toLowerCase() : null, filter.getUser() != null ?
                                userRepository.findByAlias(filter.getUser()).getId() : null,
                        filter.getType() != null ?
                                tipoRepository.findByDescripcion(filter.getType()).getIdTipo() : null);

 */

        //Valido si el filtro me devuelve información y hago dto response
        if (!recetas.isEmpty()) {
            for (Receta receta : recetas) {
                RecetaDto dto = recetaToDto(receta, filter);
                recetaDtos.add(dto);
            }
        }
        else {
            List<Long> notIngredientsIds = new ArrayList<>();
            List<Long> ingredientsIds = new ArrayList<>();

            //Busco los id de los ingredientes filtrados
            if (filter.getIngredient() != null) {
                for (String nombre : filter.getIngredient()) {
                    ingredientsIds.add(ingredienteRepository.findByNombre(nombre).getIdIngrediente());
                }
            }
            //Busco los id de los not ingredientes filtrados
            if (filter.getNotIngredient() != null) {
                for (String nombre : filter.getNotIngredient()) {
                    notIngredientsIds.add(ingredienteRepository.findByNombre(nombre).getIdIngrediente());
                }
            }

            //Aplico filtro en base a los ids de filtros de ingredientes
            List<Utilizado> utilizados = utilizadoRepository.findByIngredients
                    (ingredientsIds, notIngredientsIds);

            //Valido si el filtro me devuelve información y hago dto repsonse
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

    @Override
    public RecetaDto submitReceta(RecetaDto recetaDto) {

        Receta receta = new Receta();
        receta.setDescripcion(recetaDto.getReceta().getDescripcion());
        receta.setCantidadPersonas(recetaDto.getReceta().getCantidadPersonas());
        receta.setNombre(recetaDto.getReceta().getNombre());
        receta.setFoto(recetaDto.getReceta().getFoto());
        receta.setIdUsuario(recetaDto.getReceta().getIdUsuario());
        receta.setPorciones(recetaDto.getReceta().getPorciones());
        receta.setTag(recetaDto.getReceta().getTag());
        recetaRepository.save(recetaDto.getReceta());

        //Guardo los ingredientes con sus respectivas cantidades en utilizado
        for(IngredienteDto ingredienteDto : recetaDto.getIngredienteConCantidad()){
            Utilizado utilizado = new Utilizado();
            utilizado.setIdReceta(recetaDto.getReceta().getIdReceta());
            utilizado.setIdIngrediente(ingredienteRepository.findByNombre(ingredienteDto.getNombre()).getIdIngrediente());
            utilizado.setCantidad(ingredienteDto.getCantidad());
            utilizado.setIdUnidad(unidadRepository.findByDescripcion(ingredienteDto.getMedida()).getIdUnidad());
            utilizadoRepository.save(utilizado);
        }

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




    private RecetaDto utilizadoToRecetaDto(Utilizado utilizado) {
        Receta receta = recetaRepository.findById(utilizado.getIdReceta()).orElse(null);
        RecetaDto dto = recetaToDto(receta, null);
        return dto;
    }


    public RecetaDto recetaToDto(Receta receta, RecetaFilterDto filter) {

        RecetaDto dto = new RecetaDto();
        List<PasoDto> pasosList = new ArrayList<>();
        List<IngredienteDto> ingredientes = new ArrayList<>();

        //Traigo todos los utilizados de la receta y filtro por ingrediente y not ingrediente
        List<Utilizado> recetaUtilizado = utilizadoRepository.findByIdReceta(receta.getIdReceta());
        for (Utilizado utilizado : recetaUtilizado) {
            IngredienteDto ingDto = filterUtilizado(utilizado, filter);
            ingredientes.add(ingDto);
        }

        Optional<Tipo> tag = tipoRepository.findById(receta.getTag());
        dto.setTagString(tag.get().getDescripcion());
        dto.setReceta(receta);
        dto.setCreatorNickname(userRepository.findById(receta.getIdUsuario()).get().getAlias());
        //Agrego ingrediente con su respectiva cantidad al dto response
        dto.setIngredienteConCantidad(ingredientes);

        List<Paso> pasos = pasoRepository.findByIdReceta(receta.getIdReceta());
        for (Paso paso : pasos) {
            PasoDto pasoDto = createPasoDto(paso);
            pasosList.add(pasoDto);
        }
        dto.setPasos(pasosList);
        return dto;
    }


    public IngredienteDto filterUtilizado(Utilizado utilizado, RecetaFilterDto filter){
        IngredienteDto ingDto = new IngredienteDto();
        Optional<Ingrediente> ing = ingredienteRepository.findById(utilizado.getIdIngrediente());
        //Valido con los filtros de ingredient y notIngredient
        if (filter.getNotIngredient() != null && !filter.getNotIngredient().contains(ing)) {
            if(filter.getIngredient() != null && filter.getIngredient().contains(ing)){
                ing.ifPresent(i -> {
                    ingDto.setNombre(i.getNombre());
                    ingDto.setMedida(unidadRepository.findById(utilizado.getIdUnidad()).get().getDescripcion());
                    ingDto.setCantidad(utilizado.getCantidad());
                });
            }
            else if(filter.getIngredient() == null){
                ing.ifPresent(i -> {
                    ingDto.setNombre(i.getNombre());
                    ingDto.setMedida(unidadRepository.findById(utilizado.getIdUnidad()).get().getDescripcion());
                    ingDto.setCantidad(utilizado.getCantidad());
                });
            }
        }
        else{
            ing.ifPresent(i -> {
                ingDto.setNombre(i.getNombre());
                ingDto.setMedida(unidadRepository.findById(utilizado.getIdUnidad()).get().getDescripcion());
                ingDto.setCantidad(utilizado.getCantidad());
            });
        }
        return ingDto;
    }

    public PasoDto createPasoDto(Paso paso){
        PasoDto pasoDto = new PasoDto();
        List<Multimedia> multimedia = multimediaRepository.findByIdPaso(paso.getIdPaso());
        pasoDto.setMultimedia(multimedia.stream()
                .map(Multimedia::getUrlContenido).collect(toList()));
        pasoDto.setIdPaso(paso.getIdPaso());
        pasoDto.setDescripcion(paso.getTexto());

        return pasoDto;
    }

}