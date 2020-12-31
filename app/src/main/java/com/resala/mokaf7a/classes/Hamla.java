package com.resala.mokaf7a.classes;

public class Hamla {
    public String branch;
    public int boys_count;
    public int girls_count;
    public String road;
    public String date;
    public String key;

    public Hamla(String branch, int boys_count, int girls_count, String road, String date) {
        this.branch = branch;
        this.boys_count = boys_count;
        this.girls_count = girls_count;
        this.road = road;
        this.date = date;
    }

    public Hamla() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
