package com.marilinmoda.jym.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = false)  // Esto lanzará una excepción si hay campos no definidos
public class Personal {
    private Integer perId;
    private Integer tdocId;
    private String nroDoc;
    private String apellido;
    private String nombre;
    private Integer actId;
}
