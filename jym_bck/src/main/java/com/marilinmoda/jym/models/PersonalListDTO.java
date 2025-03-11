package com.marilinmoda.jym.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
// @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class PersonalListDTO {
    private Integer perId;
    private Integer ciaId;
    private Short perTdocId;
    private String dni;
    private String perApellido;
    private String perNombre;         
    private String nombres;        
    private Short actId;         
    private String actDescri;         
    private Integer usuId;
    private String acceso;
}
