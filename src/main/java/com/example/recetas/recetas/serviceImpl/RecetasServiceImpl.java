package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.*;
import com.example.recetas.recetas.model.*;
import com.example.recetas.recetas.repository.*;
import com.example.recetas.recetas.service.CalificacionService;
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
                ingredientsId);

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

    @Override
    public RecetaPorUsuario submitRecetaForLater(Long id, String alias) {
        Optional<Receta> receta = recetaRepository.findById(id);
        Usuario usuario = userRepository.findByAlias(alias);
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
        for (Paso paso : pasos) {
            PasoDto pasoDto = createPasoDto(paso);
            pasosList.add(pasoDto);
        }
        dto.setPasos(pasosList);

        List<Calificacion> calificaciones = calificacionService.findByIdReceta(receta.getIdReceta());
        dto.setCalificacion(calificacionService.getAverageValueByReceta(calificaciones));

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