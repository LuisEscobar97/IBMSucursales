package com.ibm.academia.RESTSucursal.models.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.academia.RESTSucursal.models.entities.Dummy;
import com.ibm.academia.RESTSucursal.models.entities.Sucursal;
import com.ibm.academia.RESTSucursal.utils.SucursalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.tags.form.SelectTag;

import java.util.*;

@Service
public class SucursalServiceImpl implements ISucursalService {
    @Autowired
    private RestTemplate clienteRest;

    Logger logger = LoggerFactory.getLogger(SucursalServiceImpl.class);

    @Override
    public List<Sucursal> buscarTodos() {
        List<Sucursal> sucursales= new ArrayList<>();
        String sucuralesCadena="";
        char sucursalesArray[];
        ObjectMapper mapper=new ObjectMapper();
        Map<String, Object >servicios;
        Map<String, Object >sucursalesMap= new HashMap<>();

        try {
            sucuralesCadena=clienteRest.getForObject("https://www.banamex.com/localizador/jsonP/json5.json",String.class);
            sucursalesArray=sucuralesCadena.toCharArray();
            sucuralesCadena="{"+sucuralesCadena.substring(14,sucursalesArray.length-2);
            Dummy dummy=mapper.readValue(sucuralesCadena,Dummy.class);
            servicios= (Map<String, Object>) dummy.getServicios().get("Servicios");

            for (String t:servicios.keySet()){
                for (String s:((Map<String, Object>) servicios.get(t)).keySet()){
                    for(String u:((Map<String, Object>) ((Map<String, Object>) servicios.get(t)).get(s)).keySet()){
                        sucursalesMap.put(u,((Map<String, Object>) ((Map<String, Object>) servicios.get(t)).get(s)).get(u));
                    }
                }
            }
            for (String v:sucursalesMap.keySet())
            {
                Sucursal sucursal= new Sucursal();

                sucursal.setId(Integer.parseInt(((ArrayList<String>) sucursalesMap.get(v)).get(0)));
                sucursal.setEstado(((ArrayList<String>) sucursalesMap.get(v)).get(17));
                sucursal.setTIpoSucursal(((ArrayList<String>) sucursalesMap.get(v)).get(19));
                sucursal.setLattitud(Double.parseDouble(((ArrayList<String>) sucursalesMap.get(v)).get(16)));
                sucursal.setLongitud(Double.parseDouble(((ArrayList<String>) sucursalesMap.get(v)).get(15)));
                sucursales.add(sucursal);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return sucursales;
    }

    @Override
    public List<Sucursal> buscarSucursalesPorGPS( Double latitud, Double longitud,List<Sucursal> sucursales) {
        List<Sucursal>sucursalesCercanas = new ArrayList<>();
        Double distancia=0.0;
        try {

            for (Sucursal sucursal:sucursales) {
                distancia=SucursalUtil.distanciaObjetos(sucursal.getLattitud(),sucursal.getLongitud(),latitud,longitud);
                if (distancia<=20)
                    sucursalesCercanas.add(sucursal);
            }
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return sucursalesCercanas;
    }

    @Override
    public String buscarTodosS() {
        String sucursales="";
        char sucursalesArray[];
        ObjectMapper mapper=new ObjectMapper();
        Map<String, Object >servicios;
        Map<String, Object >sucursalesMap= new HashMap<>();
        Set<String>tags;
        try {

             sucursales=clienteRest.getForObject("https://www.banamex.com/localizador/jsonP/json5.json",String.class);
             sucursalesArray=sucursales.toCharArray();
             sucursales="{"+sucursales.substring(14,sucursalesArray.length-2);

            Dummy dummy=mapper.readValue(sucursales,Dummy.class);
            //logger.info(String.valueOf(dummy.getServicios().get("Servicios")));
            servicios= (Map<String, Object>) dummy.getServicios().get("Servicios");
            tags=servicios.keySet();

            for (String t:tags){
                for (String s:((Map<String, Object>) servicios.get(t)).keySet()){
                    for(String u:((Map<String, Object>) ((Map<String, Object>) servicios.get(t)).get(s)).keySet()){
                        sucursalesMap.put(u,((Map<String, Object>) ((Map<String, Object>) servicios.get(t)).get(s)).get(u));
                    }
                }
            }
        logger.info(((ArrayList<String>) sucursalesMap.get("83")).toString());

        } catch (Exception e) {
         logger.info(e.getMessage());
        }

        return sucursales;
    }

}
