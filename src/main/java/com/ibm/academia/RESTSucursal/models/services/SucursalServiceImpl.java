package com.ibm.academia.RESTSucursal.models.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.academia.RESTSucursal.models.entities.Dummy;
import com.ibm.academia.RESTSucursal.models.entities.Sucursal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SucursalServiceImpl implements ISucursalService {
    @Autowired
    private RestTemplate clienteRest;

    Logger logger = LoggerFactory.getLogger(SucursalServiceImpl.class);

    @Override
    public List<Sucursal> buscarTodos() {

        //List<Sucursal> Sucursales= Arrays.asList( clienteRest.getForObject("https://www.banamex.com/localizador/jsonP/json5.json",Sucursal[].class));



        return null;
    }

    @Override
    public List<Sucursal> buscarSucursalesPorGPS(String estado, String municipio, Double latitud, Double longitud) {
        return null;
    }

    @Override
    public String buscarTodosS() {
        String sucursales=clienteRest.getForObject("https://www.banamex.com/localizador/jsonP/json5.json",String.class);
        Dummy dummy=null;
        ObjectMapper mapper=new ObjectMapper();
        try {
             dummy=mapper.readValue(sucursales,Dummy.class);
        } catch (JsonProcessingException e) {
         logger.info(e.getMessage());
        }
        return dummy.getJsonCallback().toString();
    }
}
