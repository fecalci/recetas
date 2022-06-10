package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Usuario;
import com.example.recetas.recetas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDto usuario){
        return ResponseEntity.ok().body(userService.firstRegister(usuario));
    }

    @PostMapping(value="register/endRegister")
    public ResponseEntity<Usuario> endRegister(@RequestBody FinalUserDto usuario){
        return ResponseEntity.ok().body(userService.endRegister(usuario));
    }

    @PutMapping(value="resetPassword")
    public ResponseEntity<Usuario> resetPassword(String password, String validationCode){
        return ResponseEntity.ok().body(userService.resetPassword(password,validationCode));
    }

    @GetMapping(value="getValidationCode")
    public String getValidationCode(String mail){
        return "Se envió un código al correo solicitado!";
    }

    @PostMapping(value="login")
    public ResponseEntity<String> login(String username, String password){
        Boolean validUser = userService.isValidUser(username,password);
        if(validUser.equals(true))
            return ResponseEntity.ok().body("Usuario logueado con éxito");
        return ResponseEntity.badRequest().body("Credenciales incorrectas");
    }


}