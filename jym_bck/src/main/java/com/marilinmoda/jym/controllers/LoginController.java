package com.marilinmoda.jym.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marilinmoda.jym.models.LoginValidacion;
import com.marilinmoda.jym.services.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> inicioSesion) {
        Map<String, Object> response = new HashMap<>();
        System.out.println("JSON: "+inicioSesion);
        System.out.println("Correo: " + inicioSesion.get("email"));
        System.out.println("Password: " + inicioSesion.get("password"));

        String email = (String) inicioSesion.get("email");
        String pass = (String) inicioSesion.get("password");

        Object loginResult = loginService.validateLogin(email, pass);

        // System.out.println("Errores: "+loginResult);
        // System.out.println("Errores1: "+loginResult instanceof String);
            
        if (loginResult instanceof String) {
            // PARA MANEJAR LOS ERRORES DE LA BASE
            response.put("message", loginResult);
            response.put("status", 400);  

            return ResponseEntity.badRequest().body(response);
        } else if (loginResult != null) {
            // SI TODO VA BIEN ME DEVUELVE EL RESULTADO DEL LOGIN
            LoginValidacion logval = (LoginValidacion) loginResult;
            System.out.println("Password encriptado: " + logval.getUsu_key_access());

            response.put("message", "Datos obtenidos correctamente");
            response.put("body", loginResult);
            response.put("status", 200);    

            return ResponseEntity.ok(response);
        }
        // SI EL RESULTADO ES NULO, SIGNIFICA QUE EL CORREO O LA CONTRASEÑA SON INCORRECTAS
        response.put("message", "Correo y/o Contraseña incorrectos.");
        response.put("status", 404);
        
        return ResponseEntity.badRequest().body(response);
    }
}
