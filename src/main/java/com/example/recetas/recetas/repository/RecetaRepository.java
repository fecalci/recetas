package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetaRepository extends JpaRepository<Receta,Long> {

    List<Receta> findByNombreOrIdUsuarioOrTag(String nombre, Long idUsuario, Long tag);


    Optional<Receta> findById(Long id);


}
