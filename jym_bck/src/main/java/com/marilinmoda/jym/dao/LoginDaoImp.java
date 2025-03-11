package com.marilinmoda.jym.dao;

import org.springframework.stereotype.Repository;

import com.marilinmoda.jym.config.MapperJSON;
import com.marilinmoda.jym.models.LoginValidacion;

import jakarta.persistence.EntityManager;
// import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
// import jakarta.persistence.StoredProcedureQuery;
// import jakarta.transaction.Transactional;

@Repository
public class LoginDaoImp implements LoginDao<Object, Integer> {
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Object getLogin(String email, String pass) {
        try {
            System.out.println("Correo dao: "+email);
            System.out.println("Password dao: "+pass);
            // Query query = em.createNativeQuery("SELECT * FROM mys_login_validacion_correo(:email)", LoginValidacion.class);
            Query query = em.createNativeQuery("SELECT * FROM mys_login_validacion_correo(:email)");

            query.setParameter("email", email);
            // StoredProcedureQuery sp = em.createStoredProcedureQuery("ufn_login_validacion_correo", LoginValidacion.class);

            // sp.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            // sp.setParameter(1, email);

            String jsonResult = (String) query.getSingleResult();

            System.out.println("JSON DAO: "+jsonResult);

            LoginValidacion login=MapperJSON.convertir_Json_to_Object(jsonResult, LoginValidacion.class);

            return login;
        } catch (PersistenceException e) {
            // Capturar el mensaje de excepción lanzado desde PostgreSQL
            String errorMessage = e.getMessage();  // Obtener el mensaje de la excepción
            String filteredMessage = null;

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
        } catch (Exception e) {
            // Manejar cualquier otra excepción
            return null;
        }
    }

}
