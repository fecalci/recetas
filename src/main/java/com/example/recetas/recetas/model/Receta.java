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
    private Long idUsuario;

    private String nombre;
    private String descripcion;
    private String foto;
    private int porciones;
    private int cantidadPersonas;
    private List<Tipo> tag;
    private List<Ingrediente> ingredientes;
    private List<Preparacion> preparacion;

    public List<Tipo> getTag() {
        return tag;
    }

    public void setTag(List<Tipo> tag) {
        this.tag = tag;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Preparacion> getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(List<Preparacion> preparacion) {
        this.preparacion = preparacion;
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
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

    public Receta(Long idReceta,Long idUsuario,String nombre, String descripcion, String foto, int porciones, List<Tipo> tag,
                  List<Ingrediente> ingredientes, List<Preparacion> preparacion) {
        this.idReceta=idReceta;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.porciones = porciones;
        this.cantidadPersonas = cantidadPersonas;
        this.tag = tag;
        this.ingredientes = ingredientes;
        this.preparacion = preparacion;
    }
}
