package com.ibm.academia.RESTSucursal.models.entities;

import com.ibm.academia.RESTSucursal.enums.TIpoSucursal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SucursalDTO implements Serializable {

    private Integer id;
    private String estado;
    private Double lattitud;
    private Double longitud;
    private String tIpoSucursal;


}
