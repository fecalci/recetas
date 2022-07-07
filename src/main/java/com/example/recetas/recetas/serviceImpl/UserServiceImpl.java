package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Usuario;
import com.example.recetas.recetas.model.VerificationToken;
import com.example.recetas.recetas.repository.TokenRepository;
import com.example.recetas.recetas.repository.UserRepository;
import com.example.recetas.recetas.service.EmailService;
import com.example.recetas.recetas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EmailService emailService;

    @Override
    public Usuario firstRegister(UsuarioDto userDto) {
        Usuario user = new Usuario();
        user.setMail(userDto.getMail());
        user.setAlias(userDto.getAlias());
        return userRepository.save(user);
    }

    @Override
    public Usuario endRegister(FinalUserDto finalUserDto) {
        Usuario user = userRepository.findByAlias(finalUserDto.getAlias());
        if(user != null)
            return mapDtoToUser(user, finalUserDto);
        else
            return null;
    }


    @Override
    public Usuario isValidUser(String email, String password) {
        Usuario user = userRepository.findByMailAndPassword(email, password);
        if (user != null)
            return user;
        return null;
    }

    @Override
    public String resetPassword(String token, String password) {
        VerificationToken verificationToken = emailService.getVerificationToken(token);
        Usuario user = userRepository.findByMail(verificationToken.getMail());
        user.setPassword(password);
        userRepository.save(user);
        return "Se cambió la contraseña con éxito!";
    }

    public Usuario mapDtoToUser(Usuario user,FinalUserDto finalUserDto){
        user.setApellido(finalUserDto.getApellido());
        user.setAvatar(finalUserDto.getAvatar());
        user.setFechaNacimiento(finalUserDto.getFechaNacimiento());
        user.setNombre(finalUserDto.getNombre());
        user.setPassword(finalUserDto.getPassword());
        return userRepository.save(user);
    }
}
