package com.apps.project.appstation.Objects;

/**
 * Created by jeison on 05/09/2015.
 */
public class DatosAntena {

    String fecha;
    String temperatura;
    String humedad;
    //String idantena;

    public DatosAntena( String temperatura, String humedad,String fecha) {
        this.fecha = fecha;
        this.temperatura = temperatura;
        this.humedad = humedad;
       // this.idantena = idantena;
    }

    public DatosAntena() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

   /* public String getIdantena() {
        return idantena;
    }

    public void setIdantena(String idantena) {
        this.idantena = idantena;
    }*/
}
