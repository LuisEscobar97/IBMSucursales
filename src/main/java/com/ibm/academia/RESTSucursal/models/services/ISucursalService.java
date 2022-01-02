package com.ibm.academia.RESTSucursal.models.services;

import com.ibm.academia.RESTSucursal.models.entities.SucursalDTO;

import java.util.List;

public interface ISucursalService {
    public List<SucursalDTO> buscarTodos();
    public List<SucursalDTO> buscarSucursalesPorGPS(Double latitud, Double longitud, List<SucursalDTO> sucursales);


}
