package com.example.recetas.recetas.serviceImpl;

import com.example.recetas.recetas.dto.UsuarioDto;
import com.example.recetas.recetas.model.Usuario;
import com.example.recetas.recetas.model.VerificationToken;
import com.example.recetas.recetas.repository.TokenRepository;
import com.example.recetas.recetas.repository.UserRepository;
import com.example.recetas.recetas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void confirmRegistration(UsuarioDto user) {
        String token = generateString("123456789",6);
        createVerificationToken(user, token);

        String recipientAddress = user.getMail();
        String subject = "Confirmación de Mail CocinAR";
        String message = "Bienvenido a CocinAR!! Éste es tu código de confirmación para terminar tu registro:" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

    public void createVerificationToken(UsuarioDto user, String token) {
        VerificationToken myToken = new VerificationToken(token, user.getAlias());
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public static String generateString(String characters, int length)
    {
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
