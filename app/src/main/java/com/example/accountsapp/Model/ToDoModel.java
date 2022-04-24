package com.example.accountsapp.Model;


public class ToDoModel {
    private int id,net_amount;
    private String name,status,place,note,date,username,password,logged_in,phonenumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(int net_amount) {
        this.net_amount = net_amount;
    }

    public String getLogged_in() {
        return logged_in;
    }

    public void setLogged_in(String logged_in) {
        this.logged_in = logged_in;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
