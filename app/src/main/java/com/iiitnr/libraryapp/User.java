package com.iiitnr.libraryapp;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class User {
    public User() {
    }

    private String name, email;
    private List<Integer> book = new ArrayList<Integer>();
    private List<Integer> fine = new ArrayList<Integer>();
    private List<Integer> re = new ArrayList<Integer>();
    private List<Timestamp> date = new ArrayList<Timestamp>();
    private int enroll;
    private int card;
    private int type;

    public int getTot_fine() {
        return tot_fine;
    }

    public void setTot_fine(int tot_fine) {
        this.tot_fine = tot_fine;
    }

    private int tot_fine;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public User(String name, String email, int enroll, int card, int type) {
        this.name = name;
        this.email = email;
        this.enroll = enroll;
        this.card = card;
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

    public List<Integer> getBook() {
        return book;
    }

    public void setBook(List<Integer> book) {
        this.book = book;
    }

    public List<Integer> getFine() {
        return fine;
    }

    public void setFine(List<Integer> fine) {
        this.fine = fine;
    }

    public List<Integer> getRe() {
        return re;
    }

    public void setRe(List<Integer> re) {
        this.re = re;
    }

    public List<Timestamp> getDate() {
        return date;
    }

    public void setDate(List<Timestamp> date) {
        this.date = date;
    }

    public int getEnroll() {
        return enroll;
    }

    public void setEnroll(int enroll) {
        this.enroll = enroll;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }
}



