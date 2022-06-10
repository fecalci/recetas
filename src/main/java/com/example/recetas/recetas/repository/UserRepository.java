package com.example.recetas.recetas.repository;

import com.example.recetas.recetas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Usuario findByAlias(String alias);

    Usuario findByMailAndPassword(String email, String password);
}
