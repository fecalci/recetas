package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.model.Calificacion;
import com.example.recetas.recetas.model.Receta;
import com.example.recetas.recetas.repository.CalificacionRepository;
import com.example.recetas.recetas.repository.RecetaRepository;
import com.example.recetas.recetas.repository.UserRepository;
import com.example.recetas.recetas.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private CalificacionService calificacionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecetaRepository recetaRepository;


    @Override
    public Calificacion submitCalificacion(String nickName ,Long number, Long recipeId) {
        Calificacion calificacionObj = new Calificacion();
        calificacionObj.setCalificacion(Math.toIntExact(number));
        calificacionObj.setIdReceta(recipeId);
        calificacionObj.setIdUsuario(userRepository.findByAlias(nickName).getId());
        Optional<Receta> recipe = recetaRepository.findById(recipeId);
        calificacionRepository.save(calificacionObj);

        List<Calificacion> calificaciones = calificacionService.findByIdReceta(recipe.get().getIdReceta());
        recipe.get().setCalificacion(getAverageValueByReceta(calificaciones, recipe.get()));
        return calificacionObj;
    }

    @Override
    public List<Calificacion> findByIdReceta(Long recipeId) {
        return calificacionRepository.findByIdReceta(recipeId);
    }

    @Override
    public int getAverageValueByReceta(List<Calificacion> calificaciones, Receta receta) {
        int finalValue = 0;
        for(Calificacion calificacion : calificaciones){
            finalValue += calificacion.getCalificacion();
        }
        finalValue = finalValue != 0 ? finalValue / calificaciones.size() : 0;
        receta.setCalificacion(finalValue);
        recetaRepository.save(receta);
        return finalValue;
    }
}
