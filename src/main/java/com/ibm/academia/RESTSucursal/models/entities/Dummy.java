package com.ibm.academia.RESTSucursal.models.entities;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dummy implements Serializable {

    private Map<String, Object >Servicios;

    @JsonAnySetter
    public void addOtherInfo(String propertyKey, Object value) {
        if (this.Servicios == null) {
            this.Servicios = new HashMap<>();
        }
        this.Servicios.put(propertyKey, value);
    }
}
