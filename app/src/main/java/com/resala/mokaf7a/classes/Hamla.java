package com.resala.mokaf7a.classes;

import androidx.annotation.NonNull;

public class Hamla implements Comparable {
    public String branch;
    public int boys_count;
    public int girls_count;
    public String road;
    public String date;
    public String key;

    public int allMeals = 0;
    public int allClothes = 0;
    public int allBlankets = 0;
    public int allCases = 0;

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

    public void setAllMeals(int allMeals) {
        this.allMeals = allMeals;
    }

    public void setAllClothes(int allClothes) {
        this.allClothes = allClothes;
    }

    public void setAllBlankets(int allBlankets) {
        this.allBlankets = allBlankets;
    }

    public void setAllCases(int allCases) {
        this.allCases = allCases;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        /* For Ascending order*/
        //        return this.count-compared;

        /* For Descending order do like this */
        //    return compared.compareTo(this.mosharkaType);

        String[] comparedDate = ((Hamla) o).date.split("-", 3);
        int comparedDate_int = Integer.parseInt(comparedDate[2]) * 365 + Integer.parseInt(comparedDate[1]) * 30 + Integer.parseInt(comparedDate[0]);
        String[] otherDate = this.date.split("-", 3);
        int otherDate_int = Integer.parseInt(otherDate[2]) * 365 + Integer.parseInt(otherDate[1]) * 30 + Integer.parseInt(otherDate[0]);

        return otherDate_int - comparedDate_int;
    }
}
