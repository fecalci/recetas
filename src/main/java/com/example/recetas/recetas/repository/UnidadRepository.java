package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad,Long> {

    Unidad findByDescripcion(String descripcion);

}
