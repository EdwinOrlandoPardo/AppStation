package com.apps.project.appstation.Objects;

/**
 * Created by Edwin on 08/09/2015.
 */
public class MisAntenas {

    public int idAntena;
    public String nombreAntena;

    public MisAntenas(){

    }


    public MisAntenas(int idAntena,  String nombreAntena) {
        this.idAntena = idAntena;

        this.nombreAntena = nombreAntena;
    }

    public int getIdAntena() {
        return idAntena;
    }

    public void setIdAntena(int idAntena) {
        this.idAntena = idAntena;
    }

    public String getNombreAntena() {
        return nombreAntena;
    }

    public void setNombreAntena(String nombreAntena) {
        this.nombreAntena = nombreAntena;
    }

}
