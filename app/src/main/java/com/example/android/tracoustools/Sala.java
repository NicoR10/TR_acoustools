package com.example.android.tracoustools;

import java.io.Serializable;

public class Sala implements Serializable {

    private String nombre;
    private double volumen, suptotal;

    public Sala(String nombre, double volumen, double suptotal) {
        this.nombre = nombre;
        this.volumen = volumen;
        this.suptotal = suptotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getSuptotal() {
        return suptotal;
    }

    public void setSuptotal(double suptotal) {
        this.suptotal = suptotal;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + '\n'
                + "Volumen: " + String.valueOf(volumen) +" [m3]"+ '\n'
                +"Superficie Total: " + String.valueOf(suptotal)+ " [m2]";
    }
}
