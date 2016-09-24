package com.example.yoo.appeb;

/**
 * Created by angelicabarreda on 23/09/16.
 */

public class gastosDetail_list {
    private String nombreConcepto;
    private String cantidadGD;
    private String totalGD;
    private String fechaGD;
    private String idGD;
    private String idRGD;

    public  gastosDetail_list(String nombreConcepto, String cantidadGD,String totalGD ,String fechaGD, String idGD, String idRGD) {
        this.nombreConcepto = nombreConcepto;
        this.cantidadGD = cantidadGD;
        this.totalGD = totalGD;
        this.fechaGD = fechaGD;
        this.idGD = idGD;
        this.idRGD = idRGD;
    }

    public String getNombreConcepto() {
        return nombreConcepto;
    }

    public void setNombreConcepto(String nombreConcepto) {
        this.nombreConcepto= nombreConcepto;
    }

    public String getCantidadGD() {
        return cantidadGD;
    }

    public void setCantidadGD(String cantidadGD) {
        this.cantidadGD = cantidadGD;
    }

    public String getTotalGD() {
        return totalGD;
    }

    public void setTotalGD(String totalGD) {
        this.totalGD= totalGD;
    }

    public String getFechaGD() {
        return fechaGD;
    }

    public void setFechaGD(String fechaGD) {
        this.fechaGD= fechaGD;
    }

    public String getIdGD() {
        return idGD;
    }

    public void setIdGD(String idGD) {
        this.idGD= idGD;
    }

    public String getIdRGD() {
        return idRGD;
    }

    public void setIdRGD(String idRGD) {
        this.idRGD= idRGD;
    }
}
