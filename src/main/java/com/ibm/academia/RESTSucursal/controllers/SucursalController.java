package com.ibm.academia.RESTSucursal.controllers;

import com.ibm.academia.RESTSucursal.models.entities.Sucursal;
import com.ibm.academia.RESTSucursal.models.services.ISucursalService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            List<Sucursal>listaSucursales= sucursalesService.buscarTodos();
            return new ResponseEntity<List<Sucursal>>(listaSucursales, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("Algo salio ma con la peticion",HttpStatus.CONFLICT);
        }

    }
    @GetMapping("/listar/sucursales")
    private ResponseEntity<?> listaSucursalesCercanas(@RequestParam(name = "latitud")Double latitud,@RequestParam(name = "longitud") Double longitud){
        try {
            List<Sucursal>listaSucursales= sucursalesService.buscarTodos();
            List<Sucursal>listaSucursalesCercanas=sucursalesService.buscarSucursalesPorGPS(latitud,longitud,listaSucursales);
            return new ResponseEntity<List<Sucursal>>(listaSucursalesCercanas, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>("Algo salio ma con la peticion",HttpStatus.CONFLICT);
        }

    }
}
