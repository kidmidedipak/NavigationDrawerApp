package com.dipak.navigationdrawerapp.model;

public class Contact {

    private int id;
    private String name;
    private String phone;
    private int profileImageResId; // Resource ID of the profile image


    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Contact(int id, String name, String phone, int profileImageResId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.profileImageResId = profileImageResId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }
}
