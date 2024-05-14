package com.multi.ksh.cart.controller;

import com.multi.ksh.cart.model.Dao.CartDao;
import com.multi.ksh.cart.model.Dto.CartDto;
import com.multi.ksh.cart.service.CartService;
import com.multi.ksh.cart.view.CartMenu;

import java.util.ArrayList;
import java.util.List;

public class CartController {

    private CartService cartService = new CartService();

    public void displayCartList(int id) {

        CartMenu cartMenu = new CartMenu();

        cartMenu.displayCartList(cartService.displayCartList(id));

    }

    public void deleteCartProduct(int id, int cartProductId) {

        cartService.deleteCartProduct(id, cartProductId);

    }

    public int displayPoint(int id) {

        int userPoint = cartService.displayPoint(id);

        return userPoint;

    }

    public int displayPayAmount(int id, int usedPoint, int payType) {

        // 현재 db의 카트 리스트를 가져온다.
        List<CartDto> cartList = cartService.displayCartList(id); // 이게 지금 ArrayList<CartDto>이고, 각 인덱스에 한 행이 들어 있다.

        int payAmount = 0;

        // 한행씩 돌리면서 그 행의 price와 amount를 곱한 뒤, 돌릴 때마다 +=한다.
        for(CartDto cartDto : cartList) {
            payAmount += cartDto.getPrice() * cartDto.getAmount();
        }

        if(payType == 3) {
            return (int) Math.floor((payAmount - usedPoint) * 0.95);
        }
        else {
            return payAmount - usedPoint;
        }

    }

    public int insertOrderList(int id, int payAmount, String address) {

        int orderId = cartService.insertOrderList(id, payAmount, address);

        return orderId;

    }

    public void resetCart(int id) {

        cartService.resetCart(id);

    }

    public int deductPoint(int id, int usedPoint) {

        int result = cartService.deductPoint(id, usedPoint);

        return result;

    }

    public int isShoppingcartEmpty(int id) {

        int shoppingcartRowCount = cartService.isShoppingcartEmpty(id);

        return shoppingcartRowCount;

    }

    public void insertOrderProductList(int id, int orderId) {

        ArrayList<CartDto> cartList = cartService.displayCartList(id);

        cartService.insertOrderProductList(orderId, cartList);

    }

}
