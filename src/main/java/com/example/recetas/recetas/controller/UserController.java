package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Usuario;
import com.example.recetas.recetas.repository.UserRepository;
import com.example.recetas.recetas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value ="/register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDto usuario) throws Exception {
        if(userRepository.findByAlias(usuario.getAlias()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias");
        }
        else if(userRepository.findByMail(usuario.getMail()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "mail");
        }
        else{
            return ResponseEntity.ok().body(userService.firstRegister(usuario));
        }
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
    public ResponseEntity<String> login(@RequestBody UsuarioDto user){
        Boolean validUser = userService.isValidUser(user.getMail(),user.getPassword());
        if(validUser.equals(true))
            return ResponseEntity.ok().body("Usuario logueado con éxito");
        return ResponseEntity.badRequest().body("Credenciales incorrectas");
    }


}