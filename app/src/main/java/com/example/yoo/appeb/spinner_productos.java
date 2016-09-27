package com.example.yoo.appeb;

/**
 * Created by angelicabarreda on 27/09/16.
 */

public class spinner_productos {
    private String idproductos;

    private String nombreProducto;

    private String preciosProducto;

    public spinner_productos(String idproductos, String nombreProducto, String preciosProducto)
    {
        super();
        this.idproductos= idproductos;
        this.nombreProducto = nombreProducto;
        this.preciosProducto = preciosProducto;
    }



    public String getIdproductos()
    {
        return idproductos;
    }

    public void setIdproductos(String idproductos)
    {
        this.idproductos = idproductos;
    }

    public String getNameProducto()
    {
        return nombreProducto;
    }

    public void setNameProducto(String nombreProducto)
    {
        this.nombreProducto = nombreProducto;
    }

    public String getPrecioProduct()
    {
        return preciosProducto;
    }

    public void setPreciosProduct(String preciosProducto)
    {
        this.preciosProducto = preciosProducto;
    }
}
