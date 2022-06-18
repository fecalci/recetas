package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.dto.RecetaDto;
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
                    "WHERE :nombre is NULL or r.nombre  LIKE LOWER(CONCAT('%',:nombre,'%')) " +
                    "AND :idUsuario is NULL or r.idUsuario = :idUsuario " +
                    "AND :tag is NULL or r.tag = :tag ")
    List<Receta> findByNombreOrIdUsuarioOrTag(@Param("nombre") String nombre,
                                              @Param("idUsuario") Long idUsuario,
                                              @Param("tag") Long tag);


    @Query(value = "select r.id_receta, r.id_usuario, r.nombre, r.descripcion,r.foto,  r.porciones, r.cantidad_personas,r.tag from receta r\n" +
            "inner join utilizado u on r.id_receta = u.id_receta\n" +
            "inner join ingrediente i on i.id_ingrediente = u.id_ingrediente\n" +
            "where (:nombre is null or r.nombre like lower('%:nombre%') )\n" +
            "and (:idUsuario is null or r.id_usuario = :idUsuario)\n" +
            "and (:tag is null or r.tag = :tag)\n" +
            "and (:ingredients is null or u.id_ingrediente in (:ingredients))\n" +
            "and (:notIngredients is null or u.id_ingrediente not in (:notIngredients))", nativeQuery = true)
    List<Receta> findRecetasByFilter(@Param("nombre") String nombre,
                                     @Param("idUsuario") Long idUsuario,
                                     @Param("tag") Long tag,
                                     @Param("ingredients") List<Long> ingredients,
                                     @Param("notIngredients") List<Long> notIngredients);



    Optional<Receta> findById(Long id);


}
