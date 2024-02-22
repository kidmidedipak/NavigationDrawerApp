package com.dipak.navigationdrawerapp.model;

public class Product {

    int img_id;
    String img_url;
    String img_info;
    String img_title;
    String img_sub_title;

    public Product(int img_id, String img_url, String img_info, String img_title, String img_sub_title) {
        this.img_id = img_id;
        this.img_url = img_url;
        this.img_info = img_info;
        this.img_title = img_title;
        this.img_sub_title = img_sub_title;
    }

    public int getImg_id() {
        return img_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getImg_info() {
        return img_info;
    }

    public String getImg_title() {
        return img_title;
    }

    public String getImg_sub_title() {
        return img_sub_title;
    }
}
