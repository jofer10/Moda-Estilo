package com.marilinmoda.jym.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.marilinmoda.jym.config.MapperJSON;
import com.marilinmoda.jym.models.PersonalListDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Repository
public class PersonalDaoImp implements PersonalDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Object addOrupdatePersonal(PersonalListDTO per) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM mys_personal_ins_upd(?1,?2,?3,?4,?5,?6,?7)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, per.getPerId());
            query.setParameter(2, per.getCiaId());
            query.setParameter(3, per.getPerTdocId());
            query.setParameter(4, per.getDni());
            query.setParameter(5, per.getPerApellido());
            query.setParameter(6, per.getPerNombre());
            query.setParameter(7, per.getActId());
            
            String jsonResult = (String) query.getSingleResult();
            System.out.println("DAO: Verificar que llega: "+jsonResult);

            return MapperJSON.convertir_Json_to_Object_List(jsonResult);
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
        }
    }

    @Override
    public Object personalList(Integer cia_id, String dni, String nombres) {
        try {
            // Query query = em.createNativeQuery("SELECT * FROM ufn_sis_personal_list(?1,?2)",PersonalListDTO.class);
            Query query = em.createNativeQuery("SELECT * FROM mys_usuarios_list(?1,?2,?3)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, cia_id);
            query.setParameter(2, dni);
            query.setParameter(3, nombres);

            String jsonResult = (String) query.getSingleResult();
            
            // @SuppressWarnings("unchecked")
            // List<PersonalListDTO> res = (List<PersonalListDTO>) query.getResultList();
            // System.out.println("DAO: Verificar que llega: "+jsonResult);

            Map<String, Object> resultMap=MapperJSON.convertir_Json_to_Object_List(jsonResult);

            System.out.println("DAO JSON: "+resultMap.get("data"));

            if (!resultMap.get("sql_err").equals("0")) {
                System.out.println("Error: "+resultMap.get("sql_msn"));

                return resultMap;
            }

            return resultMap;
        } catch(Exception e){
            return null;
        }
    }

    @Override
    public Object deletePersonal(Integer per_id) {
        try {
            Query query = em.createNativeQuery("SELECT mys_personal_switch_del(?1)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, per_id);
            
            String jsonResult = (String) query.getSingleResult();
            System.out.println("DAO Personal: Verificar que llega: "+jsonResult);

            return MapperJSON.convertir_Json_to_Object_List(jsonResult);
        } catch (PersistenceException e) {
            return e.getMessage();
        }
    }

}
