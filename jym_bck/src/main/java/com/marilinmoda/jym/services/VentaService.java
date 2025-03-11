package com.marilinmoda.jym.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marilinmoda.jym.dao.VentasDao;
import com.marilinmoda.jym.models.VentasDTO;
import com.marilinmoda.jym.models.VentasDetalleDTO;
import com.marilinmoda.jym.models.VentasPagosDTO;

@Service
public class VentaService {
    @Autowired
    private VentasDao vd;

    public List<VentasDTO> getListVentas(String search, String fecha_desde, String fecha_hasta){
        List<VentasDTO> resVenta = vd.getVentas(search,fecha_desde,fecha_hasta);
        
        return resVenta;
    }

    public List<VentasDetalleDTO> getListVentasDetalle(Integer venta_id){
        List<VentasDetalleDTO> resVenta = vd.getVentasDetalle(venta_id);
        
        return resVenta;
    }

    public List<VentasPagosDTO> getListVentasPagos(Integer venta_id){
        List<VentasPagosDTO> resVenta = vd.getVentasPagoDetalle(venta_id);
        
        return resVenta;
    }

    public Object addVentas(VentasDTO venta) {
        Object resVenta = vd.addVentas(venta);
        
        if (resVenta instanceof String) {
            return resVenta;
        } else if (resVenta != null) {
            return resVenta;
        }

        return null;
    }

    public Object addVentasDetalle(VentasDetalleDTO ventasDetail) {
        Object resVenta = vd.addVentasDetalle(ventasDetail);
        
        if (resVenta instanceof String) {
            return resVenta;
        } else if (resVenta != null) {
            return resVenta;
        }

        return null;
    }

    public Object addOrupdVentasPagos(VentasPagosDTO ventaPago) {
        Object resVentaPago = vd.addOrupdVentasPago(ventaPago);
        
        if (resVentaPago instanceof String) {
            return resVentaPago;
        } else if (resVentaPago != null) {
            return resVentaPago;
        }

        return null;
    }
}
