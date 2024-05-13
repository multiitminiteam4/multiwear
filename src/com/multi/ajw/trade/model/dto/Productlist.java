package com.multi.ajw.trade.model.dto;

import com.mysql.cj.util.DnsSrv;

import java.sql.Date;

public class Productlist {
    private int product_id;
    private String product_name;
    private int price;
    private Date created_at;

    public  Productlist(){}
    public Productlist(int product_id, String product_name, int price, Date created_at) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.created_at = created_at;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public String toString() {
        return product_id+", "+product_name+", "+price+", "+created_at;
    }
}
