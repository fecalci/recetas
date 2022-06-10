package com.example.recetas.recetas.service;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Usuario;
import org.springframework.stereotype.Service;

public interface UserService {

    Usuario firstRegister(UsuarioDto userDto);

    Usuario endRegister(FinalUserDto finalUserDto);

    Usuario resetPassword(String password, String validationCode);

    Boolean isValidUser(String email, String password);
}
