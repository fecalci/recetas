package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {

    //Optional<Tipo> findById(Long id);
    Tipo findByDescripcion(String descripcion);

    Optional<Tipo> findById(Long id);

}
