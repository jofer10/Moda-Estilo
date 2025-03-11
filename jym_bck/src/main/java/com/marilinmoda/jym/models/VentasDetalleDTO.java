package com.marilinmoda.jym.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class VentasDetalleDTO {
    private Integer vnt_prod_id;
    private Integer venta_id;
    private String vprod_descri;
    private BigDecimal vprod_price;
    private Boolean vprod_estado;
}
