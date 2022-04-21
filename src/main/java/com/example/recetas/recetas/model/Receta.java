package com.example.recetas.recetas.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Receta {
    private Long idReceta;
    @ManyToOne
    @JoinColumn(name ="idUsuario")
    private Usuario idUsuario;
    private String nombre;
    private String descripcion;
    private byte [] foto;
    private int porciones;
    private int cantidadPersonas;
    private Long idTipo;

}
