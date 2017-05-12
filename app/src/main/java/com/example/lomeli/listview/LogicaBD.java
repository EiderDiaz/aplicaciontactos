package com.example.lomeli.listview;

public class LogicaBD {

    public static final String NOMBRETBLCONTACTOS="tblcontactos";
    public static final String CAMPO_ID = "idcontactos";
    public static final String CAMPO_NOMBRE="nombrecontacto";
    public static final String CAMPO_TELEFONO="telefonocontacto";
    public static final String CAMPO_EMAIL="emailcontacto";

    public static final String CREARTBLCONTACTOS=" CREATE TABLE "+NOMBRETBLCONTACTOS+" ("+CAMPO_ID+" integer primary key autoincrement , "+CAMPO_NOMBRE+" text not null , "+CAMPO_TELEFONO+" text , "+CAMPO_EMAIL+" text) ;";

    public static final String INSERTARREGISTRO (String nombre, String telefono, String email){
        return "INSERT INTO "+ NOMBRETBLCONTACTOS+ "("+ CAMPO_NOMBRE+" , "+CAMPO_TELEFONO+" , "+CAMPO_EMAIL+
                ") VALUES ('"+ nombre+ "' , '"+ telefono+"' , '" + email + "');";
    }

    public static final String SELECCIONARDATOSTABLACONTACTOS= "SELECT * FROM "+NOMBRETBLCONTACTOS;



}
