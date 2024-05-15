package com.multi.ksh.run;

import com.multi.ksh.cart.view.CartMenu;

public class CartRun {

    public static void main(String[] args) {

        int id = 1; // user id 로그인 시 받아오기 지금은 일단 1

        new CartMenu().cartMenu(id);

    } // main end

}
