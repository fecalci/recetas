package com.example.recetas.recetas.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table
public class Usuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String mail;
    private String alias;
    private Boolean habilitado;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String avatar;
    private String tipoUsuario;
    private String password;

    public Usuario() {
    }

    public Usuario(String mail, String alias, Boolean habilitado, String nombre, String tipoUsuario) {
        this.mail = mail;
        this.alias = alias;
        this.habilitado = habilitado;
        this.nombre = nombre;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(Long id, String mail, String alias, Boolean habilitado, String nombre, String avatar, String tipoUsuario) {
        this.id = id;
        this.mail = mail;
        this.alias = alias;
        this.habilitado = habilitado;
        this.nombre = nombre;
        this.avatar = avatar;
        this.tipoUsuario = tipoUsuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
