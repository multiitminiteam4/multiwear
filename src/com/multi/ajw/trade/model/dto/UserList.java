package com.multi.ajw.trade.model.dto;

import java.util.Date;

public class UserList {
    private String user_id;
    private int product_id;
    private String isapproved;
    private int price;
    private Date date;

   public UserList(){

   }

    public UserList(String user_id, int product_id, String isapproved, int price, Date date) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.isapproved = isapproved;
        this.price = price;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(String isapproved) {
        this.isapproved = isapproved;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String toString() {
        return user_id+", "+product_id+", "+isapproved+", "+price+", "+date;
    }
    public String list(){
       return user_id;
    }
}
