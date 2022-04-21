package com.example.recetas.recetas.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Calificacion {
    private Long idCalificacion;
    @ManyToOne
    @JoinColumn(name ="idUsuario")
    private Usuario idUsuario;
    @ManyToOne
    @JoinColumn(name ="idReceta")
    private Receta idReceta;
    private int calificacion;
    private String comentarios;

    public Calificacion() {
    }

    public Calificacion(Long idCalificacion, Usuario idUsuario, Receta idReceta, int calificacion, String comentarios) {
        this.idCalificacion = idCalificacion;
        this.idUsuario = idUsuario;
        this.idReceta = idReceta;
        this.calificacion = calificacion;
        this.comentarios = comentarios;
    }

    public Calificacion(int calificacion, String comentarios) {
        this.calificacion = calificacion;
        this.comentarios = comentarios;
    }

    public Long getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Long idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Receta getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Receta idReceta) {
        this.idReceta = idReceta;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "idCalificacion=" + idCalificacion +
                ", idUsuario=" + idUsuario +
                ", idReceta=" + idReceta +
                ", calificacion=" + calificacion +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
}
