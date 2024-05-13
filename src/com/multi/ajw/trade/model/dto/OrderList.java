package com.multi.ajw.trade.model.dto;

public class OrderList {
    private String product_name;
    private int amount;
    private int price;
    private int t_price;
    private int order_id;
    public OrderList(){

    }

    public OrderList(String product_name, int amount, int price, int t_price,int order_id) {
        this.product_name = product_name;
        this.amount = amount;
        this.price = price;
        this.t_price = t_price;
        this.order_id = order_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getT_price() {
        return t_price;
    }

    public void setT_price(int t_price) {
        this.t_price = t_price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return order_id+", "+product_name+", "+amount+", "+price+", "+t_price;
    }
    public String checktoString() {
        return "";//order_id+", "+user_id+", "+product_id+", "+product_name+", "+price+", "+visible;
    }
}
