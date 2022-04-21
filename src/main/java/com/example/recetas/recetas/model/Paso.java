package com.example.recetas.recetas.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Paso {
    private Long idPaso;
    @ManyToOne
    @JoinColumn(name ="idReceta")
    private Receta idReceta;
    private int nroPaso;
    private String texto;
}
