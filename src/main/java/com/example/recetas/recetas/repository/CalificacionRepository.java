package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion,Long> {

    List<Calificacion> findByIdReceta(Long idReceta);
}
