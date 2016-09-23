package com.example.yoo.appeb;

/**
 * Created by jordigarcia on 9/23/16.
 */

public class productos_list {
    private String nombreProducto;
    private String precioProducto;
    private String idProducto;

    public productos_list(String nombreProducto, String precioProducto,String idProducto ) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombreProducto;
    }

    public void setNombre(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getprecioProducto() {
        return precioProducto;
    }

    public void setprecioProducto(String precioProducto) {
        this.precioProducto = precioProducto;
    }
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto= idProducto;
    }
}
