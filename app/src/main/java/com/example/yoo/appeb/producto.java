package com.example.yoo.appeb;

/**
 * Created by KissPK on 13/09/2016.
 */

public class producto {
    //variables
    private int id_producto;
    private int id_usuario;
    private String nombre;
    private int precio;


    //Getters and setters
    public int getIdProducto() {
        return id_producto;
    }

    public void setIdProducto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getIdUsuario() {
        return id_usuario;
    }

    public void setIdUsuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }


}
