package com.marilinmoda.jym.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ClienteDTO {
    private Integer cliente_id;
    private Short cli_tdoc;
    private String tipo_doc;
    private String cli_nrodoc;
    private String nombres;
    private String cli_apellido;
    private String cli_nombre;
    private String pho_numero;
    private Integer dir_id;
    private String dir_calle;
}
