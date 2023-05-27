package com.example.foodcounter;

public class Products {
    long id;
    String name;
    double calories;
    double uglevod;
    double zhiri;
    double belki;

    public Products(long id, String name, double calories, double uglevod, double zhiri, double belki) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.uglevod = uglevod;
        this.zhiri = zhiri;
        this.belki = belki;
    }
}
