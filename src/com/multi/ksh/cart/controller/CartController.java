package com.multi.ksh.cart.controller;

import com.multi.ksh.cart.model.Dto.CartDto;
import com.multi.ksh.cart.service.CartService;
import com.multi.ksh.cart.view.CartMenu;

import java.util.ArrayList;

public class CartController {

    private CartService cartService = new CartService();

    public void displayCartList() {

        CartMenu cartMenu = new CartMenu();

        cartMenu.displayCartList(cartService.displayCartList());

    }

    public void deleteCartProduct(int cartProductId) {

        cartService.deleteCartProduct(cartProductId);

    }


}
