package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Utilizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilizadoRepository extends JpaRepository<Utilizado,Long> {


    @Query(value=
    "SELECT u FROM Utilizado u "+
    "WHERE :ingredients is NULL or u.idIngrediente in(:ingredients) " +
    "AND :notIngredients is NULL or u.idIngrediente not in (:notIngredients)")
    List<Utilizado> findByIngredients(@Param("ingredients") List<Long> ingredients,
                                      @Param("notIngredients") List<Long> notIngredients);


    List<Utilizado> findByIdReceta(Long id);

    List<Utilizado> findByIdIngrediente(Long id);

}
