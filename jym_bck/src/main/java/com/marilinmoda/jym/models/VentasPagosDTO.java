package com.marilinmoda.jym.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class VentasPagosDTO {
    private Integer pago_id;
    private Integer venta_id;
    private BigDecimal pago_monto;
    private String pago_fecha;
    private String pago_hora;
    private Boolean exists_img;
    private String pago_img_path;
    private Short pago_estado_id;
    private String pago_estado;
}
