package com.example.recetas.recetas.model;

import javax.persistence.*;

@Entity
@Table
public class Receta {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idReceta;

    @ManyToOne
    @JoinColumn(name ="idUsuario")
    private Usuario idUsuario;

    private String nombre;
    private String descripcion;
    private byte [] foto;
    private int porciones;
    private int cantidadPersonas;

    @ManyToOne
    @JoinColumn(name="idTipo")
    private Tipo idTipo;

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getPorciones() {
        return porciones;
    }

    public void setPorciones(int porciones) {
        this.porciones = porciones;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
    }

    public Receta(Long idReceta, Usuario idUsuario, String nombre, String descripcion, byte[] foto, int porciones, int cantidadPersonas, Tipo idTipo) {
        this.idReceta = idReceta;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.porciones = porciones;
        this.cantidadPersonas = cantidadPersonas;
        this.idTipo = idTipo;
    }
}
