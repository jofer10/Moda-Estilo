package com.marilinmoda.jym.config;

import java.util.HashMap;
import java.util.Map;

public class Response {
    static Map<String, Object> response = new HashMap<>();

    public static Map<String, Object> success(String message, Object resService, Object status) {
        // Limpiamos el mapa antes de agregar nueva información
        response.clear();

        response.put("message", message);
        response.put("body", resService);
        response.put("status", status); 

        return response;
    }

    public static Map<String, Object> error(String message, Object resService, Object status) {
        // Limpiamos el mapa antes de agregar nueva información
        response.clear();

        response.put("message", message);
        response.put("error", resService);
        response.put("status", status); 

        return response;
    }
}
