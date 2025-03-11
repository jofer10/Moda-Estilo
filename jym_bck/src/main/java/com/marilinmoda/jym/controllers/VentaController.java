package com.marilinmoda.jym.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marilinmoda.jym.config.MapperJSON;
import com.marilinmoda.jym.config.Response;
import com.marilinmoda.jym.models.VentasDTO;
import com.marilinmoda.jym.models.VentasDetalleDTO;
import com.marilinmoda.jym.models.VentasPagosDTO;
import com.marilinmoda.jym.services.VentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/venta")
public class VentaController {
    @Autowired
    private VentaService vs;

    @SuppressWarnings("deprecation")
    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getVentasList(@Valid @RequestBody Map<String, Object> payload) {
        System.out.println("JSON: "+payload);
        System.out.println("Buscar: " + payload.get("search"));
        System.out.println("Fecha Inicio: " + payload.get("fecha_desde"));
        System.out.println("Fecha Fin: " + payload.get("fecha_hasta"));

        // Validar que los campos del payload sean los correctos.
        if (!payload.containsKey("search") || !payload.containsKey("fecha_desde") || !payload.containsKey("fecha_hasta")) {
            throw new HttpMessageNotReadableException("La estructura del JSON es erróneo.");
        }

        String search = (String) payload.get("search");
        String fecha_desde = (String) payload.get("fecha_desde");
        String fecha_hasta = (String) payload.get("fecha_hasta");

        List<VentasDTO> resService = vs.getListVentas(search,fecha_desde,fecha_hasta);

        System.out.println("C: Objeto listado ventas: " + MapperJSON.convertidorJSON(resService));

        Map<String, Object> response = Response.success("Listado Ventas", resService, 200);

        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("deprecation")
    @GetMapping("/detalle/list/{venta_id}")
    public ResponseEntity<Map<String, Object>> getVentasDetalleList(@PathVariable String venta_id) {
        try {
            Integer newID = Integer.parseInt(venta_id);

            Object resService = vs.getListVentasDetalle(newID);

            System.out.println("C: Objeto listado venta detalle: " + MapperJSON.convertidorJSON(resService));

            Map<String, Object> response = Response.success("Listado Venta Detalle", resService, 200);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new HttpMessageNotReadableException("El parámetro solo acepta valores de tipo entero.");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createVenta(@Valid @RequestBody VentasDTO venta) {
        Map<String, Object> response = new HashMap<>();

        System.out.println("C - Venta: Añadir - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(venta));

        Object resService = vs.addVentas(venta);

        if (resService instanceof String) {
            // PARA MANEJAR LOS ERRORES DE LA BASE
            response.put("message", resService);
            response.put("status", 400);  

            return ResponseEntity.badRequest().body(response);
        } else if (resService != null) {
            // SI TODO VA BIEN ME DEVUELVE EL MENSAJE DE AÑADIR
            System.out.println("C - Añadir Venta: " + resService);
            
            response=Response.success("Venta añadida", resService, 200);

            return ResponseEntity.ok(response);
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        response.put("message", "Algunos datos son erroneos.");
        response.put("status", 404);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/detalle/add")
    public ResponseEntity<Map<String, Object>> createVentaDetalle(@Valid @RequestBody VentasDetalleDTO ventasDetail) {
        Map<String, Object> response = new HashMap<>();

        System.out.println("C - Venta Detalle: Añadir - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(ventasDetail));

        Object resService = vs.addVentasDetalle(ventasDetail);

        if (resService instanceof String) {
            // PARA MANEJAR LOS ERRORES DE LA BASE
            response.put("message", resService);
            response.put("status", 400);  

            return ResponseEntity.badRequest().body(response);
        } else if (resService != null) {
            // SI TODO VA BIEN ME DEVUELVE EL MENSAJE DE AÑADIR
            System.out.println("C - Añadir Detalle Venta: " + resService);
            
            response=Response.success("Detalle Venta Añadida", resService, 200);

            return ResponseEntity.ok(response);
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        response.put("message", "Algunos datos son erroneos.");
        response.put("status", 404);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // PARA LISTAR LOS PAGOS
    @SuppressWarnings("deprecation")
    @GetMapping("/pago/list/{venta_id}")
    public ResponseEntity<Map<String, Object>> getVentasPagosList(@PathVariable String venta_id) {
        try {
            Integer newID = Integer.parseInt(venta_id);

            Object resService = vs.getListVentasPagos(newID);

            System.out.println("C: Objeto listado ventas pagos: " + MapperJSON.convertidorJSON(resService));

            Map<String, Object> response = Response.success("Listado Pagos de Ventas", resService, 200);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new HttpMessageNotReadableException("El parámetro solo acepta valores de tipo entero.");
        }
    }

    //PARA INSERTAR LOS PAGOS DE LAS VENTAS REALIZADAS
    @PostMapping("/pago/add")
    public ResponseEntity<Map<String, Object>> createVentaPago(@Valid @RequestBody VentasPagosDTO ventaPago) {
        Map<String, Object> response = new HashMap<>();

        System.out.println("C - Venta Pago: Añadir - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(ventaPago));

        Object resService = vs.addOrupdVentasPagos(ventaPago);

        if (resService instanceof String) {
            // PARA MANEJAR LOS ERRORES DE LA BASE
            response.put("message", resService);
            response.put("status", 400);  

            return ResponseEntity.badRequest().body(response);
        } else if (resService != null) {
            // SI TODO VA BIEN ME DEVUELVE EL MENSAJE DE AÑADIR
            System.out.println("C - Añadir Venta Pago: " + resService);
            
            response=Response.success("Venta Pago agregado", resService, 200);

            return ResponseEntity.ok(response);
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        response.put("message", "Algunos datos son erroneos.");
        response.put("status", 404);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //PARA ACTUALIZAR LOS PAGOS DE LAS VENTAS REALIZADAS SEA UN MONTO ERRONEO O INSERTAR O ACTUALIZAR LA URL DE LA FOTO DEL PAGO
    @PostMapping("/pago/upd")
    public ResponseEntity<Map<String, Object>> updateVentaPago(@Valid @RequestBody VentasPagosDTO ventaPago) {
        Map<String, Object> response = new HashMap<>();

        System.out.println("C - Venta Pago: Actualizar - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(ventaPago));

        Object resService = vs.addOrupdVentasPagos(ventaPago);

        if (resService instanceof String) {
            // PARA MANEJAR LOS ERRORES DE LA BASE
            response.put("message", resService);
            response.put("status", 400);  

            return ResponseEntity.badRequest().body(response);
        } else if (resService != null) {
            // SI TODO VA BIEN ME DEVUELVE EL MENSAJE DE AÑADIR
            System.out.println("C - Actualizar Venta Pago: " + resService);
            
            response=Response.success("Venta Pago Actualizado", resService, 200);

            return ResponseEntity.ok(response);
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        response.put("message", "Algunos datos son erroneos.");
        response.put("status", 404);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<Object> handleInvalidJson(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();

        // response.put("message", "JSON contiene campos desconocidos o está malformado.");
        String message = ex.getMessage().toLowerCase();
    
        if (!message.contains("json parse error")) {
            // Si el error está relacionado con el parseo del JSON (mal formado)
            response.put("message", ex.getMessage());
        } else {
            // Si el error está relacionado con un campo faltante o mal formateado
            response.put("message", "El JSON contiene campos desconocidos o malformados.");
        }

        response.put("status", 400);
        return ResponseEntity.badRequest().body(response);
    }
}
