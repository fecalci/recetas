package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    @Query(value=
            "SELECT r FROM Receta r "+
                    "WHERE :nombre is NULL or r.nombre = :nombre " +
                    "AND :idUsuario is NULL or r.idUsuario = :idUsuario " +
                    "AND :tag is NULL or r.tag = :tag ")
    List<Receta> findByNombreOrIdUsuarioOrTag(@Param("nombre") String nombre,
                                              @Param("idUsuario") Long idUsuario,
                                              @Param("tag") Long tag);



    Optional<Receta> findById(Long id);


}
