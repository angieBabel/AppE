package com.example.yoo.appeb;

/**
 * Created by angelicabarreda on 24/09/16.
 */

public class vcontado_list {
    private String nombreProductoVc;
    private String fechaVc;
    private String precioVc;
    private String cantidadVc;
    private String modopagoVc;
    private String totalVc;
    private String idVc;
    private String idpVc;

    public vcontado_list( String nombreProductoVc, String fechaVc, String precioVc, String cantidadVc, String modopagoVc, String totalVc, String idpVc,String idVc) {
        this.nombreProductoVc = nombreProductoVc;
        this.fechaVc= fechaVc;
        this.precioVc = precioVc;
        this.cantidadVc = cantidadVc;
        this.modopagoVc = modopagoVc;
        this.totalVc = totalVc;
        this.idVc = idVc;
        this.idpVc = idpVc;
    }

    public String getNombreProductoVc() {
        return nombreProductoVc;
    }

    public void setNombreProductoVc(String nombreProductoVc) {
        this.nombreProductoVc = nombreProductoVc;
    }

    public String getPrecioVc() {
        return precioVc;
    }

    public void setPrecioVc(String precioVc) {
        this.precioVc = precioVc;
    }

    public String getCantidadVc() {
        return cantidadVc;
    }

    public void setCantidadVc(String cantidadVc) {
        this.cantidadVc= cantidadVc;
    }

    public String getModopagoVc() {
        return modopagoVc;
    }

    public void setModopagoVc(String modopagoVc) {
        this.modopagoVc= modopagoVc;
    }

    public String getIdVc() {
        return idVc;
    }

    public void setIdVc(String idVc) {
        this.idVc= idVc;
    }

    public String getFechaVc() {
        return fechaVc;
    }

    public void setFechaVc(String fechaVC) {
        this.fechaVc= fechaVc;
    }

    public String getTotalVc() {
        return totalVc;
    }

    public void setTotalVc(String totalVc) {
        this.totalVc= totalVc;
    }

    public String getidpVc() {
        return idpVc;
    }

    public void setidpVc(String idpVc) {
        this.idpVc= idpVc;
    }


}
