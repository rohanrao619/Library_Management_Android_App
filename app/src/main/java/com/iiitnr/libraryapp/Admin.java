package com.iiitnr.libraryapp;

public class Admin {

    private int type;
    private String name,email;



    public Admin() {
    }


    public Admin(int type, String name, String email) {
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
