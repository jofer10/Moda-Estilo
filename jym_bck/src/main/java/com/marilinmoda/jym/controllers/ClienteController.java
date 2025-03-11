package com.marilinmoda.jym.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marilinmoda.jym.config.MapperJSON;
import com.marilinmoda.jym.config.Response;
import com.marilinmoda.jym.models.ClienteDTO;
import com.marilinmoda.jym.services.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService cs;

    @SuppressWarnings("deprecation")
    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getClienteList(@Valid @RequestBody Map<String, Object> payload) {
        System.out.println("JSON: "+payload);
        System.out.println("Buscar: " + payload.get("search"));

        // Validar que 'search' esté presente
        if (!payload.containsKey("search")) {
            throw new HttpMessageNotReadableException("La estructura del JSON es erróneo.");
        }

        String search = (String) payload.get("search");

        Object resService = cs.getListCliente(search);

        System.out.println("C: Objeto listado cliente: " + MapperJSON.convertidorJSON(resService));

        Map<String, Object> response = Response.success("Listado Cliente", resService, 200);

        return ResponseEntity.ok(response);
    }

    @SuppressWarnings("deprecation")
    @PostMapping("/list/dir")
    public ResponseEntity<Map<String, Object>> getDireccionClienteList(@Valid @RequestBody Map<String, Object> payload) {
        System.out.println("JSON: "+payload);
        System.out.println("Buscar: " + payload.get("search"));

        // Validar que 'search' esté presente
        if (!payload.containsKey("search")) {
            throw new HttpMessageNotReadableException("La estructura del JSON es erróneo.");
        }

        String search = (String) payload.get("search");

        Object resService = cs.getListDireccionCliente(search);

        System.out.println("C: Objeto listado dirección cliente: " + MapperJSON.convertidorJSON(resService));

        Map<String, Object> response = Response.success("Listado Dirección Cliente", resService, 200);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createCliente(@Valid @RequestBody ClienteDTO cliente) {
        Map<String, Object> response = new HashMap<>();

        System.out.println("C - Cliente: Añadir - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(cliente));

        Object resService = cs.addOrupdateCliente(cliente);

        if (resService instanceof String) {
            // PARA MANEJAR LOS ERRORES DE LA BASE
            response.put("message", resService);
            response.put("status", 400);  

            return ResponseEntity.badRequest().body(response);
        } else if (resService != null) {
            // SI TODO VA BIEN ME DEVUELVE EL MENSAJE DE AÑADIR
            System.out.println("C - Añadir Cliente: " + resService);
            
            response=Response.success("Cliente agregado", resService, 200);

            return ResponseEntity.ok(response);
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        response.put("message", "Algunos datos son erroneos.");
        response.put("status", 404);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/upd")
    public ResponseEntity<Map<String, Object>> updateCliente(@Valid @RequestBody ClienteDTO cliente) {
        Map<String, Object> response = new HashMap<>();

        System.out.println("C - Cliente: Actualizar - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(cliente));

        Object resService = cs.addOrupdateCliente(cliente);

        if (resService instanceof String) {
            // PARA MANEJAR LOS ERRORES DE LA BASE
            response.put("message", resService);
            response.put("status", 400);  

            return ResponseEntity.badRequest().body(response);
        } else if (resService != null) {
            // SI TODO VA BIEN ME DEVUELVE EL MENSAJE DE AÑADIR
            System.out.println("C - Actualizar Cliente: " + resService);
            
            response=Response.success("Cliente actualizado", resService, 200);

            return ResponseEntity.ok(response);
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        response.put("message", "Algunos datos son erroneos.");
        response.put("status", 404);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @SuppressWarnings("deprecation")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Integer newID = Integer.parseInt(id);
            System.out.println("C - Cliente: Eliminar - Recibir parámetro de ruta desde Postman: " + newID);

            Object resService = cs.deleteCliente(newID);

            if (resService instanceof String) {
                // PARA MANEJAR LOS ERRORES DE LA BASE
                response.put("message", resService);
                response.put("status", 400);  

                return ResponseEntity.badRequest().body(response);
            } else if (resService != null) {
                // SI TODO VA BIEN ME DEVUELVE EL MENSAJE DE AÑADIR
                System.out.println("C - Eliminar Cliente: " + resService);
                
                response=Response.success("Cliente eliminado", resService, 200);

                return ResponseEntity.ok(response);
            }

            // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
            response.put("message", "Algunos datos son erroneos.");
            response.put("status", 404);
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            throw new HttpMessageNotReadableException("El parámetro solo acepta valores de tipo entero.");
        }
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
