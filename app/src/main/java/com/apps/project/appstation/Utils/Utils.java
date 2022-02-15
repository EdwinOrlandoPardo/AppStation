package com.apps.project.appstation.Utils;

import com.apps.project.appstation.Objects.DatosAntena;
import com.apps.project.appstation.Objects.MisAntenas;
import com.apps.project.appstation.Objects.POJORegistro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeison on 05/09/2015.
 */
public class Utils {

    //Entontramos información importante para todo el proyecto

    //datos para la conexión
    public static final String NAMESPACE = "http://tempuri.org/";
    public static final String URL = "http://192.168.0.20/ServiceAlarma.asmx";
    public static final String soapAction="http://tempuri.org/";

    //Datos del usuario

    public static String nombreUsuario;
    public static int idUsuario;
    public static String correoUsuario;
    public static String apellidoUsuario;
    public static int idAntena;
    public static String nombreAntena;
    public static ArrayList<String> idantena  = new ArrayList<>();

    public static ArrayList<DatosAntena> DatosAntena = new ArrayList<>();
    public static ArrayList<POJORegistro> ListaAlarmas = new ArrayList<>();
    public static ArrayList<MisAntenas> listaAntenas = new ArrayList<>();

}
