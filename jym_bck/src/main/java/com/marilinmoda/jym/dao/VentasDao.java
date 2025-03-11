package com.marilinmoda.jym.dao;

import java.util.List;

import com.marilinmoda.jym.models.VentasDTO;
import com.marilinmoda.jym.models.VentasDetalleDTO;
import com.marilinmoda.jym.models.VentasPagosDTO;

public interface VentasDao {
    List<VentasDTO> getVentas(String search, String fecha_desde, String fecha_hasta);
    List<VentasDetalleDTO> getVentasDetalle(Integer venta_id);
    List<VentasPagosDTO> getVentasPagoDetalle(Integer venta_id);
    Object addVentas(VentasDTO ventas);
    Object addVentasDetalle(VentasDetalleDTO ventasDetail);
    Object addOrupdVentasPago(VentasPagosDTO ventaPago);
}
