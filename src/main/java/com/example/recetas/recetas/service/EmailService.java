package com.example.recetas.recetas.service;

import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.VerificationToken;

public interface EmailService {

    void confirmRegistration(String usuario);

    VerificationToken getVerificationToken(String VerificationToken);
}
