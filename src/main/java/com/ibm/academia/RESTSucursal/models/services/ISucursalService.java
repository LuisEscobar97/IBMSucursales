package com.ibm.academia.RESTSucursal.models.services;

import com.ibm.academia.RESTSucursal.models.entities.Sucursal;

import java.util.List;

public interface ISucursalService {
    public List<Sucursal> buscarTodos();
    public List<Sucursal> buscarSucursalesPorGPS(String estado, String municipio, Double latitud, Double longitud);
    public String buscarTodosS();

}
