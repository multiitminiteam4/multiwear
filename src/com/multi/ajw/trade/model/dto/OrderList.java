package com.multi.ajw.trade.model.dto;

public class OrderList {
    private int order_id;
    private String user_id;
    private String product_name;
    private int price;
    private int product_id;
    private String visible;
    public OrderList(){

    }

    public OrderList(int order_id, String user_id, String product_name, int price, int product_id, String visible) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.product_name = product_name;
        this.price = price;
        this.product_id = product_id;
        this.visible = visible;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return order_id+", "+user_id+", "+product_id+", "+product_name+", "+price;
    }
    public String checktoString() {
        return order_id+", "+user_id+", "+product_id+", "+product_name+", "+price+", "+visible;
    }
}
