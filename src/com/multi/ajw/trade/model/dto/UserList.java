package com.multi.ajw.trade.model.dto;

import java.util.Date;

public class UserList {
    //보상판매id	제품id	제품이름	승인여부	가격	신청날짜
    private String product_name;
    private String user_id;
    private int product_id;
    private String isapproved;
    private int price;
    private int trade;
    private Date date;

   public UserList(){

   }

    public UserList(String product_name, String user_id, int product_id, String isapproved, int price, int trade, Date date) {
        this.product_name = product_name;
        this.user_id = user_id;
        this.product_id = product_id;
        this.isapproved = isapproved;
        this.price = price;
        this.trade = trade;
        this.date = date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
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

    public int getTrade() {
        return trade;
    }

    public void setTrade(int trade) {
        this.trade = trade;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String toString() {
        return trade+", "+product_id+", "+product_name+", "+isapproved+", "+price+", "+date;
    }
    public String list(){
       return user_id;
    }
}
