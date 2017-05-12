package com.example.lomeli.listview;

/**
 * Created by Lomeli on 09/03/2017.
 */

public class Contactos {

    private String nombre;
    private String celular;
    private String email;



    public Contactos(String nombre, String celular, String email) {
        this.nombre = nombre;
        this.celular = celular;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
