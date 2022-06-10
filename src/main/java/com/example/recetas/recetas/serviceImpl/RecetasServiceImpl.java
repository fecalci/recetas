package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.RecetaFilterDto;
import com.example.recetas.recetas.model.Receta;
import com.example.recetas.recetas.service.RecetaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetasServiceImpl implements RecetaService {

    @Override
    public List<Receta> getRecetasByFilter(RecetaFilterDto filter) {
        return null;
    }
}
