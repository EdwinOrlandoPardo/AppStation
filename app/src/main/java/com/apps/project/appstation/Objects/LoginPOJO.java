package com.apps.project.appstation.Objects;

/**
 * Created by Edwin on 03/09/2015.
 */
public class LoginPOJO {

    public String id;
    public String nombre;
    public String apellido;
    public String correo;
    public String contrasena;

    /*public LoginPOJO(String correo, String contrasena, String nombre, String apellido) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
