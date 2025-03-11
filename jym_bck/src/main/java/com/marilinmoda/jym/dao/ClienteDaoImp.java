package com.marilinmoda.jym.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.marilinmoda.jym.config.MapperJSON;
import com.marilinmoda.jym.models.ClienteDTO;
import com.marilinmoda.jym.models.ClienteDireccionDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

@Repository
@SuppressWarnings("unchecked")
public class ClienteDaoImp implements ClienteDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Object addOrupdateCliente(ClienteDTO cli) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_clientes_add_upd(?1,?2,?3,?4,?5,?6,?7)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, cli.getCliente_id());
            query.setParameter(2, cli.getCli_tdoc());
            query.setParameter(3, cli.getCli_nrodoc());
            query.setParameter(4, cli.getCli_apellido());
            query.setParameter(5, cli.getCli_nombre());
            query.setParameter(6, cli.getDir_calle());
            query.setParameter(7, cli.getPho_numero());

            List<Object[]> res = query.getResultList();

            System.out.println("DAO - Cliente: Verificar que llega: "+MapperJSON.convertidorJSON(res));
            
            Map<String, Object> resultCliente = new HashMap<>();
            List<Map<String, Object>> listMapCli = new ArrayList<>(); 

            resultCliente.put("mensaje", res.get(0)[0]);
            listMapCli.add(resultCliente);

            return listMapCli;
        } catch (PersistenceException e) {
            // Capturar el mensaje de excepción lanzado desde PostgreSQL
            String errorMessage = e.getMessage();  // Obtener el mensaje de la excepción
            String filteredMessage = null;

            System.out.println("DAO - Cliente: Excepción: "+errorMessage);

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
    public List<ClienteDTO> clienteList(String search) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_clientes_list(?1)",ClienteDTO.class);

            //Agregamos los parámetros para la consulta
            query.setParameter(1, search);
            
            List<ClienteDTO> res = (List<ClienteDTO>) query.getResultList();

            System.out.println("DAO - Listar Cliente: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<ClienteDireccionDTO> direccionCliList(String search) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_clientes_direccion_search(?1)", ClienteDireccionDTO.class);

            //Agregamos los parámetros para la consulta
            query.setParameter(1, search);

            List<ClienteDireccionDTO> res = query.getResultList();

            System.out.println("DAO - Listar Dirección Cliente: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Object deleteCliente(Integer cliente_id) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM ufn_sis_clientes_delete(?1)");

            //Agregamos los parámetros para la consulta
            query.setParameter(1, cliente_id);

            Object res = query.getResultList();

            System.out.println("DAO - Cliente: Verificar que llega: "+MapperJSON.convertidorJSON(res));

            return res;
        } catch (PersistenceException e) {
            // Capturar el mensaje de excepción lanzado desde PostgreSQL
            String errorMessage = e.getMessage();  // Obtener el mensaje de la excepción
            String filteredMessage = null;

            System.out.println("DAO - Cliente: Excepción: "+errorMessage);

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
