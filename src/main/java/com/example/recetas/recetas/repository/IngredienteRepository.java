package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    Optional<Ingrediente> findById(Long id);

    Ingrediente findByNombre(String nombre);
}
