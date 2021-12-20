package com.ibm.academia.RESTSucursal.controllers;

import com.ibm.academia.RESTSucursal.models.entities.Sucursal;
import com.ibm.academia.RESTSucursal.models.services.ISucursalService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private String listaSucursales(){

        return sucursalesService.buscarTodosS();
    }
}
