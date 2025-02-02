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
    public String feed_back_type;
    public String feed_back_details;
    public String first_feedback;
    public String gender;
    public String help_date;
    public Object id;
    public Object meals;
    public String name;
    public String notes;
    public String phoneNo;
    public String pushid;
    public String second_feedback;
    public String seen;
    public String state;
    public String case_name;

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
        this.blankets = "";
        this.branch = branch;
        this.case_num = "";
        this.clothes_num = "";
        this.case_name = "";
        this.date = date;
        this.feed_back = "";
        this.feed_back_type = "";
        this.gender = gender;
        this.help_date = "";
        this.id = "";
        this.meals = "";
        this.name = name;
        this.notes = notes;
        this.phoneNo = phoneNo;
        this.pushid = pushid;
        this.seen = seen;
        this.state = state;
        this.first_feedback = "";
        this.second_feedback = "";
        this.feed_back_details = "";

    }

    public Report() {

    }

    public void setId(Object id) {
        this.id = id;
    }

}
