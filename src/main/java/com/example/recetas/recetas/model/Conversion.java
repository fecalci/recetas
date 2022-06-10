package com.example.recetas.recetas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class Conversion {

    @Id
    @GeneratedValue
    private Long idConversion;

    private Long idUnidadOrigen;

    private Long idUnidadDestino;

    private float factorConversiones;

    public Long getIdConversion() {
        return idConversion;
    }

    public void setIdConversion(Long idConversion) {
        this.idConversion = idConversion;
    }

    public Long getIdUnidadOrigen() {
        return idUnidadOrigen;
    }

    public void setIdUnidadOrigen(Long idUnidadOrigen) {
        this.idUnidadOrigen = idUnidadOrigen;
    }

    public Long getIdUnidadDestino() {
        return idUnidadDestino;
    }

    public void setIdUnidadDestino(Long idUnidadDestino) {
        this.idUnidadDestino = idUnidadDestino;
    }

    public float getFactorConversiones() {
        return factorConversiones;
    }

    public void setFactorConversiones(float factorConversiones) {
        this.factorConversiones = factorConversiones;
    }
}
