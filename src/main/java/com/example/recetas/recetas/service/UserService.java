package com.example.recetas.recetas.service;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.RecetaDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Receta;
import com.example.recetas.recetas.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    Usuario firstRegister(UsuarioDto userDto);

    Usuario endRegister(FinalUserDto finalUserDto);

    Usuario isValidUser(String email, String password);

    String resetPassword(String token, String password);
}
