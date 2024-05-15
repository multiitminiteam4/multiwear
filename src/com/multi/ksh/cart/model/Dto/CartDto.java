package com.multi.ksh.cart.model.Dto;

public class CartDto {

    private int id;
    private int productId;
    private int amount;
    private String productName;
    private int price;


    // CartDto 기본생성자
    public CartDto(){}

    // CartDto 생성자
    public CartDto(int id, int productId, int amount, String productName, int price) {

        this.id = id;
        this.productId = productId;
        this.amount = amount;
        this.productName = productName;
        this.price = price;

    }

    // getter, setter, toString
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", amount=" + amount +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }

}
