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
    public String resetPassword(@RequestBody String mail){
        return "Se envió un correo para cambiar su contraseña";
    }

    @PostMapping(value="login")
    public ResponseEntity login(@RequestBody String username, String password){
        return ResponseEntity.ok("JWT-TOKEN");
    }


}