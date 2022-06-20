package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.model.Calificacion;
import com.example.recetas.recetas.repository.CalificacionRepository;
import com.example.recetas.recetas.repository.UserRepository;
import com.example.recetas.recetas.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Calificacion submitCalificacion(String nickName ,Long number, Long recipeId) {
        Calificacion calificacionObj = new Calificacion();
        calificacionObj.setCalificacion(Math.toIntExact(number));
        calificacionObj.setIdReceta(recipeId);
        calificacionObj.setIdUsuario(userRepository.findByAlias(nickName).getId());
        return calificacionRepository.save(calificacionObj);
    }

    @Override
    public List<Calificacion> findByIdReceta(Long recipeId) {
        return calificacionRepository.findByIdReceta(recipeId);
    }

    @Override
    public int getAverageValueByReceta(List<Calificacion> calificaciones) {
        int finalValue = 0;
        for(Calificacion calificacion : calificaciones){
            finalValue += calificacion.getCalificacion();
        }
        return finalValue != 0 ? finalValue / calificaciones.size() : 0;
    }
}
