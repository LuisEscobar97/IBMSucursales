package com.ibm.academia.RESTSucursal.controllers;

import com.ibm.academia.RESTSucursal.models.entities.SucursalDTO;
import com.ibm.academia.RESTSucursal.models.services.ISucursalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class SucursalController {

    @Autowired
    private ISucursalService sucursalesService;

    Logger logger = LoggerFactory.getLogger(SucursalController.class);

    /**
     *
     * @return
     */
    @GetMapping("/listar")
    private ResponseEntity<?> listaSucursales(){
        try {
            List<SucursalDTO>listaSucursales= sucursalesService.buscarTodos();
            return new ResponseEntity<List<SucursalDTO>>(listaSucursales, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("Algo salio ma con la peticion",HttpStatus.CONFLICT);
        }

    }
    /**
     * endpoint cerado para encontrar sucurslaes cercanas a las coordenadas ingresadas
     * @param latitud paramentro de la latitud el objeto para buscar sucursales cercanas
     * @param longitud paramentro de la longitud el objeto para buscar sucursales cercanas
     * @return respuesta JSON de la lista de sucursaales cercanas a las coordeneadas ingresadas
     */
    @GetMapping("/listar/sucursales")
    private ResponseEntity<?> listaSucursalesCercanas(@RequestParam(name = "latitud")Double latitud,@RequestParam(name = "longitud") Double longitud){
        Map<String, Object> respuesta = new HashMap<String, Object>();
        try {
            List<SucursalDTO>listaSucursales= sucursalesService.buscarTodos();
            List<SucursalDTO>listaSucursalesCercanas=sucursalesService.buscarSucursalesPorGPS(latitud,longitud,listaSucursales);
            return new ResponseEntity<List<SucursalDTO>>(listaSucursalesCercanas, HttpStatus.OK);
        }catch (Exception e){
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(respuesta,HttpStatus.CONFLICT);
        }

    }
}
