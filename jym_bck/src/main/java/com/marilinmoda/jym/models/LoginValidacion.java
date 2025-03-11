package com.marilinmoda.jym.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginValidacion {
    private String usu_key_access;
    private Integer usu_id;
    private Integer cia_id;
    private String cia_dns;
    private String per_nombre;
    private String cia_nombre;
}
