package com.marilinmoda.jym.dao;

import java.util.List;

import com.marilinmoda.jym.models.ClienteDTO;
import com.marilinmoda.jym.models.ClienteDireccionDTO;


public interface ClienteDao {
    Object addOrupdateCliente(ClienteDTO cli);
    Object deleteCliente(Integer cliente_id);
    List<ClienteDTO> clienteList(String search);
    List<ClienteDireccionDTO> direccionCliList(String search);
}
