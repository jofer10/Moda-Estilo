package com.marilinmoda.jym.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class VentasDTO {
    private Integer venta_id;
    private String venta_cod;
    private Integer cliente_id;
    private String cliente;
    private String pho_numero;
    private String venta_fec;
    private String hora_fec;
    private Integer usu_id;
    private Short pago_estado_id;
    private String pago_estado;
    private BigDecimal monto_debe;
    private BigDecimal vprod_price;
}
