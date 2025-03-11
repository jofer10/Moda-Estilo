package com.marilinmoda.jym.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.marilinmoda.jym.config.MapperJSON;
import com.marilinmoda.jym.models.VentasDTO;
import com.marilinmoda.jym.models.VentasDetalleDTO;
import com.marilinmoda.jym.models.VentasPagosDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Repository
@SuppressWarnings("unchecked")
public class VentasDaoImp implements VentasDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<VentasDTO> getVentas(String search, String fecha_desde, String fecha_hasta) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_ventas_list(?1,?2,?3)",VentasDTO.class);

            //Agregamos los parámetros para la consulta
            query.setParameter(1, search);
            query.setParameter(2, fecha_desde);
            query.setParameter(3, fecha_hasta);

            List<VentasDTO> res =  query.getResultList();

            System.out.println("DAO - Venta: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch (Exception e) {
            System.out.println("DAO - Venta: Excepción: "+e.getMessage());

            return null;
        }
    }

    @Override
    public Object addVentas(VentasDTO ventas) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_ventas_ins(?1,?2,?3)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, ventas.getVenta_id());
            query.setParameter(2, ventas.getCliente_id());
            query.setParameter(3, ventas.getUsu_id());

            Object res = query.getResultList();

            System.out.println("DAO - Venta: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch (PersistenceException e) {
            // Capturar el mensaje de excepción lanzado desde PostgreSQL
            String errorMessage = e.getMessage();  // Obtener el mensaje de la excepción
            String filteredMessage = null;

            System.out.println("DAO - Venta: Excepción: "+errorMessage);

            if (errorMessage.contains("ERROR:")) {
                // Extraer el mensaje posterior a "ERROR: "
                filteredMessage = errorMessage.substring(errorMessage.indexOf("ERROR:") + 7).trim();
                // Si hay saltos de línea, tomar solo la primera línea
                if (filteredMessage.contains("\n")) {
                    filteredMessage = filteredMessage.split("\n")[0];
                }
            } else {
                return null;
            }
            // System.out.println("Error1? "+filteredMessage);
            return filteredMessage;
        }
    }

    @Override
    public List<VentasDetalleDTO> getVentasDetalle(Integer venta_id) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_ventas_detalle_list(?1)",VentasDetalleDTO.class);

            //Agregamos los parámetros para la consulta
            query.setParameter(1, venta_id);

            List<VentasDetalleDTO> res = query.getResultList();

            System.out.println("DAO - Venta Detalle: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch (Exception e) {
            System.out.println("DAO - Venta Detalle: Excepción: "+e.getMessage());

            return null;
        }
    }

    @Override
    public Object addVentasDetalle(VentasDetalleDTO ventasDetail) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_ventas_detalle_ins(?1,?2,?3)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, ventasDetail.getVenta_id());
            query.setParameter(2, ventasDetail.getVprod_descri());
            query.setParameter(3, ventasDetail.getVprod_price());

            Object res = query.getResultList();

            System.out.println("DAO - Venta Detalle: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch (PersistenceException e) {
            // Capturar el mensaje de excepción lanzado desde PostgreSQL
            String errorMessage = e.getMessage();  // Obtener el mensaje de la excepción
            String filteredMessage = null;

            System.out.println("DAO - Venta Detalle: Excepción: "+errorMessage);

            if (errorMessage.contains("ERROR:")) {
                // Extraer el mensaje posterior a "ERROR: "
                filteredMessage = errorMessage.substring(errorMessage.indexOf("ERROR:") + 7).trim();
                // Si hay saltos de línea, tomar solo la primera línea
                if (filteredMessage.contains("\n")) {
                    filteredMessage = filteredMessage.split("\n")[0];
                }
            } else {
                return null;
            }
            // System.out.println("Error1? "+filteredMessage);
            return filteredMessage;
        }
    }

    @Override
    public List<VentasPagosDTO> getVentasPagoDetalle(Integer venta_id) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_ventas_pagos_detalle_list(?1)",VentasPagosDTO.class);

            //Agregamos los parámetros para la consulta
            query.setParameter(1, venta_id);

            List<VentasPagosDTO> res =  query.getResultList();

            System.out.println("DAO - Pagos Venta: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch (Exception e) {
            System.out.println("DAO - Pagos Venta: Excepción: "+e.getMessage());

            return null;
        }
    }

    @Override
    public Object addOrupdVentasPago(VentasPagosDTO ventaPago) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_ventas_pagos_detalle_ins_upd(?1,?2,?3,?4,?5)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, ventaPago.getPago_id());
            query.setParameter(2, ventaPago.getVenta_id());
            query.setParameter(3, ventaPago.getPago_monto());
            query.setParameter(4, ventaPago.getPago_img_path());
            query.setParameter(5, 1);

            List<Object[]> res = query.getResultList();

            System.out.println("DAO - Pagos Venta: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            Map<String, Object> resultVentaPago = new HashMap<>();
            List<Map<String, Object>> listMapVentaPago = new ArrayList<>(); 

            resultVentaPago.put("mensaje", res.get(0)[0]);
            listMapVentaPago.add(resultVentaPago);

            return listMapVentaPago;
        } catch (PersistenceException e) {
            // Capturar el mensaje de excepción lanzado desde PostgreSQL
            String errorMessage = e.getMessage();  // Obtener el mensaje de la excepción
            String filteredMessage = null;

            System.out.println("DAO - Pagos Venta: Excepción: "+errorMessage);

            if (errorMessage.contains("ERROR:")) {
                // Extraer el mensaje posterior a "ERROR: "
                filteredMessage = errorMessage.substring(errorMessage.indexOf("ERROR:") + 7).trim();
                // Si hay saltos de línea, tomar solo la primera línea
                if (filteredMessage.contains("\n")) {
                    filteredMessage = filteredMessage.split("\n")[0];
                }
            } else {
                return null;
            }
            // System.out.println("Error1? "+filteredMessage);
            return filteredMessage;
        }
    }

}
