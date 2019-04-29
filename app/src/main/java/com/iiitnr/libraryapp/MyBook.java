package com.iiitnr.libraryapp;

import java.util.Date;

public class MyBook {

    private int Bid;
    private String Title,Type;
    private Date Idate,Ddate;


    public MyBook(int bid, String title, String type, Date idate, Date ddate) {
        Bid = bid;
        Title = title;
        Type = type;
        Idate = idate;
        Ddate = ddate;
    }

    public MyBook() {
    }

    public int getBid() {
        return Bid;
    }

    public void setBid(int bid) {
        Bid = bid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Date getIdate() {
        return Idate;
    }

    public void setIdate(Date idate) {
        Idate = idate;
    }

    public Date getDdate() {
        return Ddate;
    }

    public void setDdate(Date ddate) {
        Ddate = ddate;
    }
}
