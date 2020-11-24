package com.resala.mokaf7a.classes;

public class Report {
    public String address;
    public String app;
    public String area;
    public Object blankets;
    public String branch;
    public Object case_num;
    public Object clothes_num;
    public String date;
    public String feed_back;
    public String feed_back_details;
    public String first_feedback; //first-feedback is the correct
    public String gender;
    public String help_date;
    public Object id;
    public Object meals;
    public String name;
    public String notes;
    public String phoneNo;
    public String pushid;
    public String second_feedback; //second-feedback is the correct
    public String seen;
    public String state;

    //for new report
    public Report(String address,
                  String area,
                  String branch,
                  String date,
                  String gender,
                  String name,
                  String notes,
                  String phoneNo,
                  String pushid,
                  String seen,
                  String state) {
        this.address = address;
        this.app = "Android";
        this.area = area;
        this.blankets = 0;
        this.branch = branch;
        this.case_num = 0;
        this.clothes_num = 0;
        this.date = date;
        this.feed_back = "";
        this.feed_back_details = "";
        this.gender = gender;
        this.help_date = "";
        this.id = "";
        this.meals = 0;
        this.name = name;
        this.notes = notes;
        this.phoneNo = phoneNo;
        this.pushid = pushid;
        this.seen = seen;
        this.state = state;
    }

    public Report() {
    }

    public void setFirst_feedback(String first_feedback) {
        this.first_feedback = first_feedback;
    }

    public void setSecond_feedback(String second_feedback) {
        this.second_feedback = second_feedback;
    }
}
