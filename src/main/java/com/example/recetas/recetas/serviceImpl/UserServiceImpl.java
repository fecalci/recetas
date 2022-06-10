package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.FinalUserDto;
import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Usuario;
import com.example.recetas.recetas.repository.UserRepository;
import com.example.recetas.recetas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Usuario firstRegister(UsuarioDto userDto) {
        Usuario user = new Usuario();
        user.setMail(userDto.getMail());
        user.setPassword(userDto.getPassword());
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
    public Usuario resetPassword(String password, String validationCode) {
        return null;
    }

    @Override
    public Boolean isValidUser(String email, String password) {
        Usuario user = userRepository.findByMailAndPassword(email, password);
        if (user != null)
            return true;
        return false;
    }

    public Usuario mapDtoToUser(Usuario user,FinalUserDto finalUserDto){
        user.setApellido(finalUserDto.getApellido());
        user.setAvatar(finalUserDto.getAvatar());
        user.setFechaNacimiento(finalUserDto.getFechaNacimiento());
        user.setHabilitado(finalUserDto.getHabilitado());
        user.setNombre(finalUserDto.getNombre());
        user.setPassword(finalUserDto.getPassword());
        return userRepository.save(user);
    }
}