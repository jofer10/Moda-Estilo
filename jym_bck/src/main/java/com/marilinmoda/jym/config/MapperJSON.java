package com.marilinmoda.jym.config;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperJSON {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertidorJSON(Object obj){
        try {
            String json = objectMapper.writeValueAsString(obj);

            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static <T> T convertir_Json_to_Object(String json, Class<T> clase){
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<String, Object> convertir_Json_to_Object_List(String json){
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
