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
import com.marilinmoda.jym.models.PersonalListDTO;
import com.marilinmoda.jym.services.PersonalService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/personal")
public class PersonalController {
    @Autowired
    private PersonalService ps;

    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getPersonalList(@Valid @RequestBody Map<String, Object> payload) {
        System.out.println("JSON: "+payload);
        System.out.println("Cia ID: " + payload.get("cia_id"));
        System.out.println("DNI: " + payload.get("dni"));
        System.out.println("NOMBRES: " + payload.get("nombres"));

        Integer cia_id = (Integer) payload.get("cia_id");
        String dni = (String) payload.get("dni");
        String nombres = (String) payload.get("nombres");

        @SuppressWarnings("unchecked")
        Map<String,Object> resService = (Map<String,Object>) ps.getLisPersonal(cia_id, dni, nombres);

        if (resService.get("sql_err").equals("0")) {
            System.out.println("C: Objeto listado persona: " + MapperJSON.convertidorJSON(resService));

            Map<String, Object> response = Response.success("Listado de personal", resService.get("data"), 200);

            return ResponseEntity.ok(response);    
        }

        Map<String, Object> response = Response.error("Error al listar", (String) resService.get("sql_msn"), HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
        
    }
    
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createPersonal(@Valid @RequestBody PersonalListDTO per) {
        System.out.println("C: Añadir - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(per));

        @SuppressWarnings("unchecked")
        Map<String,Object> resService = (Map<String,Object>) ps.addOrupdatePersonal(per);

        if (resService.get("sql_err").equals("0")) {
            System.out.println("C: Objeto añadir persona: " + MapperJSON.convertidorJSON(resService));

            Map<String, Object> response = Response.success("Añadir personal", resService.get("sql_msn"), 200);

            return ResponseEntity.ok(response);  
        } else if (resService.get("sql_err").equals("-1") || !resService.get("sql_err").equals("-1")) {
            Map<String, Object> response = Response.error("Error al añadir personal", (String) resService.get("sql_msn"), HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        Map<String, Object> response = Response.error("Algunos datos son erroneos.", "", HttpStatus.BAD_REQUEST);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updatePersonal(@Valid @RequestBody PersonalListDTO per) {
        System.out.println("C: Actualizar - Recibir JSON desde Postman: " + MapperJSON.convertidorJSON(per));

        @SuppressWarnings("unchecked")
        Map<String,Object> resService = (Map<String,Object>) ps.addOrupdatePersonal(per);

        if (resService.get("sql_err").equals("0")) {
            System.out.println("C: Objeto añadir persona: " + MapperJSON.convertidorJSON(resService));

            Map<String, Object> response = Response.success("Actualizar personal", resService.get("sql_msn"), 200);

            return ResponseEntity.ok(response);  
        } else if (resService.get("sql_err").equals("-1") || !resService.get("sql_err").equals("-1")) {
            Map<String, Object> response = Response.error("Error al actualizar personal", (String) resService.get("sql_msn"), HttpStatus.BAD_REQUEST);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
        }

        // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
        Map<String, Object> response = Response.error("Algunos datos son erroneos.", "", HttpStatus.BAD_REQUEST);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @SuppressWarnings("deprecation")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> eliminarCliente(@PathVariable String id) {
        try {
            Integer newID = Integer.parseInt(id);
            System.out.println("C - Personal: Eliminar - Recibir parámetro de ruta desde Postman: " + id);

            @SuppressWarnings("unchecked")
            Map<String,Object> resService = (Map<String,Object>) ps.deletePersonal(newID);

            if (resService.get("sql_err").equals("0")) {
                System.out.println("C: Objeto Switch persona: " + MapperJSON.convertidorJSON(resService));
    
                Map<String, Object> response = Response.success("Switch personal", resService.get("sql_msn"), 200);
    
                return ResponseEntity.ok(response);  
            } else if (resService.get("sql_err").equals("-1") || !resService.get("sql_err").equals("-1")) {
                Map<String, Object> response = Response.error("Error al activar/desactivar personal", (String) resService.get("sql_msn"), HttpStatus.BAD_REQUEST);
    
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
            }
    
            // SI EL RESULTADO ES NULO, SIGNIFICA QUE ESTA ERRONEO LA CONSULTA
            Map<String, Object> response = Response.error("Algunos datos son erroneos.", "", HttpStatus.BAD_REQUEST);
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
