package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.*;
import com.example.recetas.recetas.model.*;
import com.example.recetas.recetas.repository.*;
import com.example.recetas.recetas.service.CalificacionService;
import com.example.recetas.recetas.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    private RecetaPorUsuarioRepository recetaPorUsuarioRepository;

    @Autowired
    private CalificacionService calificacionService;


    @Override
    public List<RecetaDto> getRecetasByFilter(RecetaFilterDto filter) {
        List<RecetaDto> recetaDtos = new ArrayList<>();
        List<Long> ingredientsId = new ArrayList<>();
        List<Long> notIngredientsId = new ArrayList<>();
        List<Receta> recetas = new ArrayList<>();
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

        if(ingredientsId.isEmpty()){
            recetas  = recetaRepository.findRecetasByFilter(filter.getName() != null? filter.getName().toLowerCase() : null,
                    filter.getUser() != null ? userRepository.findByAlias(filter.getUser()).getId() : null,
                    filter.getType() != null ? tipoRepository.findByDescripcion(filter.getType()).getIdTipo() : null);
        }
        else{
            recetas  = recetaRepository.findRecetasByFilterAndIngredients(filter.getName() != null? filter.getName().toLowerCase() : null,
                    filter.getUser() != null ? userRepository.findByAlias(filter.getUser()).getId() : null,
                    filter.getType() != null ? tipoRepository.findByDescripcion(filter.getType()).getIdTipo() : null, ingredientsId);
        }

        //Valido si el filtro me devuelve información y hago dto response
        if (!recetas.isEmpty()) {
            for (Receta receta : recetas) {
                boolean flag = true;
                List<Utilizado> utilizados = utilizadoRepository.findByIdReceta(receta.getIdReceta());
                for(Utilizado u : utilizados){
                    if(notIngredientsId.contains(u.getIdIngrediente())){
                        flag = false;
                    }
                }
                if(flag){
                    RecetaDto dto = recetaToDto(receta, filter);
                    recetaDtos.add(dto);
                }
            }
        }
        return recetaDtos;
    }

    @Override
    public RecetaDto submitReceta(RecetaDto recetaDto) {

        Receta receta = dtoToReceta(recetaDto, null);

        //Guardo los ingredientes con sus respectivas cantidades en utilizado
        for(IngredienteDto ingredienteDto : recetaDto.getIngredienteConCantidad()){
            Utilizado utilizado = new Utilizado();
            utilizado.setIdReceta(receta.getIdReceta());
            utilizado.setIdIngrediente(ingredienteRepository.findByNombre(ingredienteDto.getNombre()).getIdIngrediente());
            utilizado.setCantidad(ingredienteDto.getCantidad());
            utilizado.setIdUnidad(unidadRepository.findByDescripcion(ingredienteDto.getMedida()).getIdUnidad());
            utilizadoRepository.save(utilizado);
        }

        for(PasoDto paso : recetaDto.getPasos()){
            Paso newPaso = new Paso();
            newPaso.setNroPaso(toIntExact(paso.getIdPaso()));
            newPaso.setIdReceta(receta.getIdReceta());
            newPaso.setTexto(paso.getDescripcion());
            pasoRepository.save(newPaso);

            Multimedia multimedia = new Multimedia();
            multimedia.setUrlContenido(paso.getMultimedia());
            multimedia.setIdPaso(newPaso.getIdPaso());
            multimediaRepository.save(multimedia);
        }
        return recetaDto;
    }

    public Receta dtoToReceta(RecetaDto recetaDto, Receta receta){
        if(receta != null){
            receta.setDescripcion(recetaDto.getReceta().getDescripcion());
            receta.setCantidadPersonas(recetaDto.getReceta().getCantidadPersonas());
            receta.setNombre(recetaDto.getReceta().getNombre());
            receta.setFoto(recetaDto.getReceta().getFoto());
            receta.setIdUsuario(recetaDto.getReceta().getIdUsuario());
            receta.setPorciones(recetaDto.getReceta().getPorciones());
            receta.setTag(recetaDto.getReceta().getTag());
            return recetaRepository.save(receta);
        }
        else{
            Receta recetaNew = new Receta();
            recetaNew.setDescripcion(recetaDto.getReceta().getDescripcion());
            recetaNew.setCantidadPersonas(recetaDto.getReceta().getCantidadPersonas());
            recetaNew.setNombre(recetaDto.getReceta().getNombre());
            recetaNew.setFoto(recetaDto.getReceta().getFoto());
            recetaNew.setIdUsuario(recetaDto.getReceta().getIdUsuario());
            recetaNew.setPorciones(recetaDto.getReceta().getPorciones());
            recetaNew.setTag(recetaDto.getReceta().getTag());
            return recetaRepository.save(recetaNew);
        }
    }

    @Override
    public RecetaPorUsuario submitRecetaForLater(Long id, String alias) throws Exception {
        Optional<Receta> receta = recetaRepository.findById(id);
        Usuario usuario = userRepository.findByAlias(alias);
        List<RecetaPorUsuario> recetasPorUsuario = recetaPorUsuarioRepository.findByNickUsuario(alias);

        //Valido si ya existe, si es de el o si la cant es mayor que 5
        boolean lePertenece = receta.get().getIdUsuario().equals(usuario.getId());
        boolean existe = recetasPorUsuario.stream()
                .filter(r -> r.getIdReceta().equals(receta.get().getIdReceta()))
                .count() > 0;
        if(recetasPorUsuario.size() >= 5)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "MAYOR5");
        else if(lePertenece)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PERTENECE");
        else if(existe)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "EXISTE");

        RecetaPorUsuario recetaPorUsuario = new RecetaPorUsuario();
        recetaPorUsuario.setIdReceta(receta.get().getIdReceta());
        recetaPorUsuario.setNickUsuario(alias);
        recetaPorUsuario.setIdUsuario(usuario.getId());

        return recetaPorUsuarioRepository.save(recetaPorUsuario);
    }

    @Override
    public List<RecetaPorUsuarioDto> getRecetasForLater(String alias) {
        List<RecetaPorUsuarioDto> response = new ArrayList<>();
        List<RecetaPorUsuario> recetasPorUsuario = recetaPorUsuarioRepository.findByNickUsuario(alias);

        for(RecetaPorUsuario rpu : recetasPorUsuario){
            Optional<Receta> receta = recetaRepository.findById(rpu.getIdReceta());
            RecetaPorUsuarioDto dto = new RecetaPorUsuarioDto();
            dto.setReceta(receta.get());
            dto.setNickName(userRepository.findById(receta.get().getIdUsuario()).get().getAlias());
            dto.setIdRecetaPorUsuario(rpu.getId());
            response.add(dto);
        }

        return response;
    }

    @Override
    public String deleteRecetasForLater(Long id) {
        recetaPorUsuarioRepository.deleteById(id);
        return "La receta se eliminó con éxito";
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
        if(!pasos.isEmpty()){
            for (Paso paso : pasos) {
                if(paso != null){
                    PasoDto pasoDto = createPasoDto(paso);
                    pasosList.add(pasoDto);
                }

            }
            dto.setPasos(pasosList);
            dto.setCalificacion(receta.getCalificacion());
        }


        return dto;
    }


    public IngredienteDto filterUtilizado(Utilizado utilizado, RecetaFilterDto filter){
        IngredienteDto ingDto = new IngredienteDto();
        Optional<Ingrediente> ing = ingredienteRepository.findById(utilizado.getIdIngrediente());
        //Valido con los filtros de ingredient y notIngredient
        if (filter != null && filter.getNotIngredient() != null && !filter.getNotIngredient().contains(ing)) {
            if(filter.getIngredient() != null && filter.getIngredient().contains(ing)){
                ing.ifPresent(i -> {
                    ingDto.setNombre(i.getNombre());
                    ingDto.setMedida(unidadRepository.findById(utilizado.getIdUnidad()).get().getDescripcion());
                    ingDto.setCantidad(utilizado.getCantidad());
                });
            }
            else{
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
        Multimedia multimedia = multimediaRepository.findByIdPaso(paso.getIdPaso());
        pasoDto.setMultimedia(multimedia != null? multimedia.getUrlContenido() : null);
        pasoDto.setIdPaso(paso.getIdPaso());
        pasoDto.setDescripcion(paso.getTexto());

        return pasoDto;
    }


    public List<RecetaDto> bestRecipes(){
        List<RecetaDto> response = new ArrayList<>();
        List<Receta> recetas = recetaRepository.findTop5ByOrderByCalificacionDesc();
        for(Receta receta : recetas){
            RecetaDto dto = recetaToDto(receta,null);
            response.add(dto);
        }
        return response;
    }

    @Override
    public RecetaDto existRecipe(String alias, String recipeName){
        Usuario user = userRepository.findByAlias(alias);
        Receta recipe = recetaRepository.findByIdUsuarioAndNombre(user.getId(),recipeName);
        return recetaToDto(recipe,null);
    }

    @Override
    public RecetaDto editRecipe(RecetaDto receta, Long id) {
        Optional<Receta> recipe = recetaRepository.findById(id);
        return editRecipeInfo(receta, recipe.get());
    }

    public RecetaDto editRecipeInfo(RecetaDto recetaDto, Receta receta){

        Receta editedReceta = dtoToReceta(recetaDto,receta);

        //Elimino los utilizados antiguos
        List<Utilizado> utilizadosOld = utilizadoRepository.findByIdReceta(receta.getIdReceta());
        utilizadosOld.stream().forEach(u -> utilizadoRepository.delete(u));

        //Elimino los pasos antiguos
        List<Paso> pasosOld = pasoRepository.findByIdReceta(receta.getIdReceta());
        pasosOld.stream().forEach(p -> pasoRepository.delete(p));

        //Guardo los ingredientes con sus respectivas cantidades en utilizado
        for(IngredienteDto ingredienteDto : recetaDto.getIngredienteConCantidad()){

            Utilizado utilizado = new Utilizado();
            utilizado.setIdReceta(receta.getIdReceta());
            utilizado.setIdIngrediente(ingredienteRepository.findByNombre(ingredienteDto.getNombre()).getIdIngrediente());
            utilizado.setCantidad(ingredienteDto.getCantidad());
            utilizado.setIdUnidad(unidadRepository.findByDescripcion(ingredienteDto.getMedida()).getIdUnidad());
            utilizadoRepository.save(utilizado);
        }

        for(PasoDto paso : recetaDto.getPasos()){
            Paso newPaso = new Paso();
            newPaso.setNroPaso(toIntExact(paso.getIdPaso()));
            newPaso.setIdReceta(receta.getIdReceta());
            newPaso.setTexto(paso.getDescripcion());
            pasoRepository.save(newPaso);

            Multimedia multimedia = new Multimedia();
            multimedia.setUrlContenido(paso.getMultimedia());
            multimedia.setIdPaso(newPaso.getIdPaso());
            multimediaRepository.save(multimedia);
        }
        return recetaDto;
    }

}