package com.ibm.academia.RESTSucursal.utils;

public class SucursalUtil {
    public static Double distanciaObjetos(Double latitudSucursal, Double longitudSucursal, Double latitudIngresada, Double longitudIngresada) {

        Double radioTierra = 6371.0;
        Double dLat = Math.toRadians(latitudIngresada-latitudSucursal);
        Double dLng = Math.toRadians(longitudIngresada-longitudSucursal);
        Double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                 + Math.cos(Math.toRadians(latitudSucursal)) * Math.cos(Math.toRadians(latitudIngresada)) *
                 Math.sin(dLng/2) * Math.sin(dLng/2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distancia = (Double) (radioTierra * c);
        return distancia;
    }


}
