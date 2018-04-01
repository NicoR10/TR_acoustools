package com.example.android.tracoustools;

import java.io.Serializable;

public class Superficie implements Serializable{

    private String material;
    private double area;

    public Superficie(String material, double area) {
        this.material = material;
        this.area = area;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
