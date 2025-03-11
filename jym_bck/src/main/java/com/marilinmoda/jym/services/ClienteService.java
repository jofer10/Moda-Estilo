package com.marilinmoda.jym.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marilinmoda.jym.dao.ClienteDao;
import com.marilinmoda.jym.models.ClienteDTO;
import com.marilinmoda.jym.models.ClienteDireccionDTO;

@Service
public class ClienteService {
    @Autowired
    private ClienteDao cd;

    public Object addOrupdateCliente(ClienteDTO cliente) {
        Object resCliente = cd.addOrupdateCliente(cliente);
        
        if (resCliente instanceof String) {
            return resCliente;
        } else if (resCliente != null) {
            return resCliente;
        }

        return null;
    }

    public Object deleteCliente(Integer cliente_id) {
        Object resCliente = cd.deleteCliente(cliente_id);
        
        if (resCliente instanceof String) {
            return resCliente;
        } else if (resCliente != null) {
            return resCliente;
        }

        return null;
    }

    public List<ClienteDTO> getListCliente(String search){
        List<ClienteDTO> resCliente = cd.clienteList(search);
        
        return resCliente;
    }

    public List<ClienteDireccionDTO> getListDireccionCliente(String search){
        List<ClienteDireccionDTO> resClienteDir = cd.direccionCliList(search);
        
        return resClienteDir;
    }
}
