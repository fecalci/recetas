package com.example.recetas.recetas.model;

import javax.persistence.*;

@Entity
@Table
public class Calificacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idCalificacion;

    @Column
    private Long idUsuario;

    @Column
    private Long idReceta;

    @Column
    private int calificacion;

    @Column
    private String comentarios;

    public Long getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Long idCalificacion) {
        this.idCalificacion = idCalificacion;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
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

    public Calificacion(Long idCalificacion, Long idUsuario, Long idReceta, int calificacion, String comentarios) {
        this.idCalificacion = idCalificacion;
        this.idUsuario = idUsuario;
        this.idReceta = idReceta;
        this.calificacion = calificacion;
        this.comentarios = comentarios;
    }

    public Calificacion() {
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
