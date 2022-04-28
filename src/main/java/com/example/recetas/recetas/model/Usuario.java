package com.example.recetas.recetas.model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table
public class Usuario {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String mail;
    private String nickName;
    private Boolean habilitado;
    private String nombre;
    private byte [] avatar;
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(String mail, String nickName, Boolean habilitado, String nombre, byte[] avatar, String tipoUsuario) {
        this.mail = mail;
        this.nickName = nickName;
        this.habilitado = habilitado;
        this.nombre = nombre;
        this.avatar = avatar;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(Long id, String mail, String nickName, Boolean habilitado, String nombre, byte[] avatar, String tipoUsuario) {
        this.id = id;
        this.mail = mail;
        this.nickName = nickName;
        this.habilitado = habilitado;
        this.nombre = nombre;
        this.avatar = avatar;
        this.tipoUsuario = tipoUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", nickName='" + nickName + '\'' +
                ", habilitado=" + habilitado +
                ", nombre='" + nombre + '\'' +
                ", avatar=" + Arrays.toString(avatar) +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }
}
