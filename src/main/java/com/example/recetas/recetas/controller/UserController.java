package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.exception.ApiError;
import com.example.recetas.recetas.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @PostMapping(value="register")
    public UsuarioDto register(@RequestBody UsuarioDto usuario){
        Usuario user = new Usuario();
        user.setMail(usuario.getMail());
        user.setPassword(usuario.getPassword());
        return usuario;
    }

    @PostMapping(value="register/endRegister")
    public FinalUserDto endRegister(@RequestBody FinalUserDto usuario){
        return usuario;
    }

    @PutMapping(value="resetPassword")
    public String resetPassword(String password, String validationCode){
        return "Su contraseña fue modificada con éxito!";
    }

    @GetMapping(value="getValidationCode")
    public String getValidationCode(String mail){
        return "Se envió un código al correo solicitado!";
    }

    @PostMapping(value="login")
    public ResponseEntity login(String username, String password){
        return ResponseEntity.ok("JWT-TOKEN");
    }


}