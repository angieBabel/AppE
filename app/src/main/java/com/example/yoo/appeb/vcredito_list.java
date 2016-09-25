package com.example.yoo.appeb;

/**
 * Created by angelicabarreda on 24/09/16.
 */

public class vcredito_list {
    private String nombreProductoVcred;
    private String fechaVcred;
    private String deudorVcred;
    private String deudaVcred;
    private String abonoVcred;
    private String abonoperiodoVcred;
    private String idVcred;

    public vcredito_list(String nombreProductoVcred,String fechaVcred, String deudorVcred, String deudaVcred, String  abonoVcred,String abonoperiodoVcred, String idVcred) {
        this.nombreProductoVcred = nombreProductoVcred;
        this.fechaVcred= fechaVcred;
        this.deudorVcred= deudorVcred;
        this.deudaVcred = deudaVcred;
        this.abonoVcred = abonoVcred;
        this.abonoperiodoVcred = abonoperiodoVcred;
        this.idVcred = idVcred;
    }

    public String getnombreProductoVcred() {
        return nombreProductoVcred;
    }

    public void setnombreProductoVcred(String nombreProductoVcred) {
        this.nombreProductoVcred = nombreProductoVcred;
    }

    public String getdeudorVcred() {
        return deudorVcred;
    }

    public void setdeudorVcred(String deudorVcred) {
        this.deudorVcred = deudorVcred;
    }

    public String getdeudaVcred() {
        return deudaVcred;
    }

    public void setdeudaVcred(String deudaVcred) {
        this.deudaVcred= deudaVcred;
    }

    public String getabonoVcred() {
        return abonoVcred;
    }

    public void setabonoVcred(String abonoVcred) {
        this.abonoVcred= abonoVcred;
    }

    public String getabonoperiodoVcred() {
        return abonoperiodoVcred;
    }

    public void abonoperiodoVcred(String abonoperiodoVcred) {
        this.abonoperiodoVcred= abonoperiodoVcred;
    }

    public String getfechaVcred() {
        return fechaVcred;
    }

    public void setfechaVcred(String fechaVcred) {
        this.fechaVcred= fechaVcred;
    }

    public String getidVcred() {
        return idVcred;
    }

    public void setidVcred(String idVcred) {
        this.idVcred= idVcred;
    }
}
