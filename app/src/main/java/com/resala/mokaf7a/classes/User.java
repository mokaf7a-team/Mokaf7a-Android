package com.resala.mokaf7a.classes;

import static com.resala.mokaf7a.LoginActivity.branches;

public class User {
    public String branch;
    public String email;

    public User() {
        this.branch = branches[9];
        this.email = " ";
    }

    public User(String branch, String email) {
        this.branch = branch;
        this.email = email;
    }
}

