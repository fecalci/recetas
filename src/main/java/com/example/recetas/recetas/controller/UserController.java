package com.example.recetas.recetas.controller;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Usuario;
import com.example.recetas.recetas.model.VerificationToken;
import com.example.recetas.recetas.repository.UserRepository;
import com.example.recetas.recetas.service.EmailService;
import com.example.recetas.recetas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;


    @PostMapping(value ="/register")
    public ResponseEntity<Usuario> register(@RequestBody UsuarioDto usuario) throws Exception {
        if(userRepository.findByAlias(usuario.getAlias()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alias");
        }
        else if(userRepository.findByMail(usuario.getMail()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "mail");
        }
        else{
            emailService.confirmRegistration(usuario);
            return ResponseEntity.ok().body(userService.firstRegister(usuario));
        }
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {

        VerificationToken verificationToken = emailService.getVerificationToken(token);
        Usuario user = userRepository.findByAlias(verificationToken.getAlias());
        Calendar cal = Calendar.getInstance();
        user.setHabilitado(true);
        userRepository.save(user);
        return "Usuario confirmado con éxito";
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
    public ResponseEntity<Usuario> login(@RequestBody UsuarioDto user){
        Usuario validUser = userService.isValidUser(user.getMail(),user.getPassword());
        if(validUser != null){
            return ResponseEntity.ok().body(validUser);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Credenciales erroneas");
    }


}