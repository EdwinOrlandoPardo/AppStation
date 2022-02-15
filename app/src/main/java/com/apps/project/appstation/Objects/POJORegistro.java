package com.apps.project.appstation.Objects;

import java.io.Serializable;

/**
 * Created by Edwin on 12/09/2015.
 */
public class POJORegistro implements Serializable {

    private int id;
    private String nameAlarma, tempeMax, tempeMin, humeMax, humeMin;

    public POJORegistro(int id, String nameAlarma, String tempeMax, String tempeMin, String humeMax, String humeMin) {

        this.id=id;
        this.nameAlarma = nameAlarma;
        this.tempeMax = tempeMax;
        this.tempeMin = tempeMin;
        this.humeMax = humeMax;
        this.humeMin = humeMin;
    }


    public int getId(){return id;}

    public void setId(int id) {this.id = id;}

    public String getNameAlarma() {
        return nameAlarma;
    }

    public void setNameAlarma(String nameAlarma) {
        this.nameAlarma = nameAlarma;
    }

    public String getTempeMax() {
        return tempeMax;
    }

    public void setTempeMax(String tempeMax) {
        this.tempeMax = tempeMax;
    }

    public String getTempeMin() {
        return tempeMin;
    }

    public void setTempeMin(String tempeMin) {
        this.tempeMin = tempeMin;
    }

    public String getHumeMax() {
        return humeMax;
    }

    public void setHumeMax(String humeMax) {
        this.humeMax = humeMax;
    }

    public String getHumeMin() {
        return humeMin;
    }

    public void setHumeMin(String humeMin) {
        this.humeMin = humeMin;
    }

}
