package com.example.recetas.recetas.model;

import javax.persistence.*;
import java.util.List;

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
    private String foto;
    private int porciones;
    private int cantidadPersonas;

    @OneToOne
    private Tipo tag;

    public Tipo getTag() {
        return tag;
    }

    public void setTag(Tipo tag) {
        this.tag = tag;
    }


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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
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

}
