package com.iiitnr.libraryapp;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name,email;
    private List<Integer> book=new ArrayList<Integer>(5);
    private List<Integer> fine=new ArrayList<Integer>(5);
    private int enroll, card,  type;

    public User()
    {

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

