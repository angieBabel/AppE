package com.example.yoo.appeb;


/**
 * Created by angelicabarreda on 26/09/16.
 */

public class spnconcept {
    private String idconceptosp;

    private String conceptosp;

    private String preciosp;

    public spnconcept(String idconceptosp, String conceptosp, String preciosp)
    {
        super();
        this.idconceptosp = idconceptosp;
        this.conceptosp = conceptosp;
        this.preciosp = preciosp;
    }



    public String getIdconceptosp()
    {
        return idconceptosp;
    }

    public void setIdconceptosp(String idconcepto)
    {
        this.idconceptosp = idconcepto;
    }

    public String getConceptosp()
    {
        return conceptosp;
    }
    /*public String toString()
    {
        return conceptosp;
    }*/

    public void setConceptosp(String conceptosp)
    {
        this.conceptosp = conceptosp;
    }

    public String getPreciosp()
    {
        return preciosp;
    }

    public void setPreciosp(String preciosp)
    {
        this.preciosp = preciosp;
    }
}
