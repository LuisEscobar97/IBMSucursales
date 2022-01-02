package com.ibm.academia.RESTSucursal.models.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.academia.RESTSucursal.exceptions.BadRequestException;
import com.ibm.academia.RESTSucursal.exceptions.NotFoundException;
import com.ibm.academia.RESTSucursal.models.entities.Sucursal;
import com.ibm.academia.RESTSucursal.models.entities.SucursalDTO;
import com.ibm.academia.RESTSucursal.utils.SucursalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SucursalServiceImpl implements ISucursalService {
    @Autowired
    private RestTemplate clienteRest;

    Logger logger = LoggerFactory.getLogger(SucursalServiceImpl.class);

    @Override
    public List<SucursalDTO> buscarTodos() {
        List<SucursalDTO> sucursales= new ArrayList<>();
        String sucuralesCadena="";
        char sucursalesArray[];
        ObjectMapper mapper=new ObjectMapper();
        Map<String, Object >servicios=null;
        Map<String, Object >sucursalesMap= new HashMap<>();

        try {
            sucuralesCadena=clienteRest.getForObject("https://www.banamex.com/localizador/jsonP/json5.json",String.class);
            sucursalesArray=sucuralesCadena.toCharArray();
            sucuralesCadena="{"+sucuralesCadena.substring(14,sucursalesArray.length-2);
            Sucursal sucursal =mapper.readValue(sucuralesCadena, Sucursal.class);
            servicios= (Map<String, Object>) sucursal.getServicios().get("Servicios");

            if(servicios.isEmpty()||servicios.keySet().isEmpty())
                throw new NotFoundException("No se encontraaron las sucursales");

            for (String t:servicios.keySet()){
                for (String s:((Map<String, Object>) servicios.get(t)).keySet()){
                    for(String u:((Map<String, Object>) ((Map<String, Object>) servicios.get(t)).get(s)).keySet()){
                        sucursalesMap.put(u,((Map<String, Object>) ((Map<String, Object>) servicios.get(t)).get(s)).get(u));
                    }
                }
            }

            for (String v:sucursalesMap.keySet())
            {
                SucursalDTO sucursalDTO = new SucursalDTO();
                sucursalDTO.setId(Integer.parseInt(((ArrayList<String>) sucursalesMap.get(v)).get(0)));
                sucursalDTO.setEstado(((ArrayList<String>) sucursalesMap.get(v)).get(17));
                sucursalDTO.setTIpoSucursal(((ArrayList<String>) sucursalesMap.get(v)).get(19));
                sucursalDTO.setLattitud(Double.parseDouble(((ArrayList<String>) sucursalesMap.get(v)).get(16)));
                sucursalDTO.setLongitud(Double.parseDouble(((ArrayList<String>) sucursalesMap.get(v)).get(15)));
                sucursales.add(sucursalDTO);
            }
            if (sucursales.isEmpty())
                throw new NotFoundException("No se encontraaron las sucursales");
        }catch (JsonProcessingException e) {
           throw new BadRequestException("no se pudo deserealizar la respuesta JSON");

        }

        return sucursales;
    }


    @Override
    public List<SucursalDTO> buscarSucursalesPorGPS(Double latitud, Double longitud, List<SucursalDTO> sucursales) {
        List<SucursalDTO>sucursalesCercanas = new ArrayList<>();
        Double distancia=0.0;
        try {

            for (SucursalDTO sucursalDTO :sucursales) {
                distancia=SucursalUtil.distanciaObjetos(sucursalDTO.getLattitud(), sucursalDTO.getLongitud(),latitud,longitud);
                if (distancia<=0.1)
                    sucursalesCercanas.add(sucursalDTO);
            }
            if (sucursalesCercanas.isEmpty())
                throw new NotFoundException("no hay sucursales cercanas a las coordenadas ingresadas");
        }catch (Exception e){
            throw new BadRequestException("algo salio mal "+e.getMessage());
        }
        return sucursalesCercanas;
    }
}
