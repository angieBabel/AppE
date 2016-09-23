package com.example.yoo.appeb;

/**
 * Created by angelicabarreda on 23/09/16.
 */

public class gastos_list {
    private String nombreRubro;
    private String totalGasto;
    private String idGasto;

    public gastos_list(String nombreRubro, String totalGasto,String idGasto ) {
        this.nombreRubro = nombreRubro;
        this.totalGasto = totalGasto;
        this.idGasto = idGasto;
    }

    public String getNombreRubro() {
        return nombreRubro;
    }

    public void setNombreRubro(String nombreRubro) {
        this.nombreRubro= nombreRubro;
    }

    public String getTotalGasto() {
        return totalGasto;
    }

    public void setTotalGasto(String totalGasto) {
        this.totalGasto = totalGasto;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto= idGasto;
    }
}
