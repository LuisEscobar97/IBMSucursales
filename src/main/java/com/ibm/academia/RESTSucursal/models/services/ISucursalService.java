package com.ibm.academia.RESTSucursal.models.services;

import com.ibm.academia.RESTSucursal.models.entities.Sucursal;

import java.util.List;

public interface ISucursalService {
    public List<Sucursal> buscarTodos();
    public List<Sucursal> buscarSucursalesPorGPS( Double latitud, Double longitud,List<Sucursal> sucursales);
    public String buscarTodosS();

}
