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
            emailService.confirmRegistration(usuario.getMail());
            return ResponseEntity.ok().body(userService.firstRegister(usuario));
        }
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = emailService.getVerificationToken(token);
        Usuario user = userRepository.findByMail(verificationToken.getMail());
        user.setHabilitado(true);
        userRepository.save(user);
        return "Usuario confirmado con éxito";
    }

    @GetMapping("/validationToken")
    public String sendToken(@RequestParam("mail") String mail){
        emailService.confirmRegistration(mail);
        return "Se envió un token al correo";
    }

    @GetMapping("/confirmToken")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token){
        VerificationToken verificationToken = emailService.getVerificationToken(token);
        if(verificationToken != null){
            return ResponseEntity.ok().body("Token Confirmado");
        }
        else
            return ResponseEntity.internalServerError().body("Token incorrecto");
    }


    @PutMapping(value="resetPassword")
    public String changePassword(@RequestParam("mail") String mail,@RequestParam("newPassword") String newPassword){
        return userService.resetPassword(mail, newPassword);
    }


    @PostMapping(value="register/endRegister")
    public ResponseEntity<Usuario> endRegister(@RequestBody FinalUserDto usuario){
        return ResponseEntity.ok().body(userService.endRegister(usuario));
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