package com.example.recetas.recetas.model;

import javax.persistence.*;

@Entity
@Table
public class Foto {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idFoto;

    private Receta idReceta;

    private String urlFoto;

    private String extension;

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }

    public Receta getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Receta idReceta) {
        this.idReceta = idReceta;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Foto(Long idFoto, Receta idReceta, String urlFoto, String extension) {
        this.idFoto = idFoto;
        this.idReceta = idReceta;
        this.urlFoto = urlFoto;
        this.extension = extension;
    }
}
