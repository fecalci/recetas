package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia,Long> {

    List<Multimedia> findByIdPaso(Long id);
}
