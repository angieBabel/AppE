package com.example.yoo.appeb;

/**
 * Created by Yoo on 23/09/2016.
 */

public class tipos_graficas {
    private String name;

    private int icon;

    public  tipos_graficas(String nombre, int icono)
    {
        super();
        this.name = nombre;
        this.icon = icono;
    }

    public String getNombre()
    {
        return name;
    }

    public void setNombre(String nombre)
    {
        this.name = nombre;
    }

    public int getIcono()
    {
        return icon;
    }

    public void setIcono(int icono)
    {
        this.icon = icono;
    }
}
