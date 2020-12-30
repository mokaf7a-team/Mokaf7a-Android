package com.resala.mokaf7a.classes;

public class Case {
    public String location;
    public String name;
    public int meals;
    public int blankets;
    public int clothes;
    public String key;

    public Case(String location, String name, int meals, int blankets, int clothes) {
        this.name = name;
        this.location = location;
        this.meals = meals;
        this.blankets = blankets;
        this.clothes = clothes;
    }

    public Case() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
