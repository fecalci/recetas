package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Paso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasoRepository extends JpaRepository<Paso, Long> {

    List<Paso> findByRecetaId(Long id);
}
