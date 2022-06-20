package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Receta;
import com.example.recetas.recetas.model.RecetaPorUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaPorUsuarioRepository extends JpaRepository<RecetaPorUsuario, Long> {

    List<RecetaPorUsuario> findByNickUsuario(String nickUsuario);

}
