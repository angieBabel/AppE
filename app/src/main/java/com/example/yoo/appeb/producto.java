package com.example.yoo.appeb;

/**
 * Created by KissPK on 13/09/2016.
 */

public class producto {
    //variables
    private String id_producto;
    private String id_usuario;
    private String nombre;
    private String precio;


    //Getters and setters
    public String getIdProducto() {
        return id_producto;
    }

    public void setIdProducto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}
