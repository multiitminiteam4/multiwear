package com.multi.ksh.cart.service;

import com.multi.ksh.cart.model.Dao.CartDao;
import com.multi.ksh.cart.model.Dto.CartDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.multi.ksh.common.JDBCTemplate.*;


public class CartService {

    private final CartDao cartDao;

    // CartService 생성자
    public CartService() {
        cartDao = new CartDao();
    }

    public ArrayList<CartDto> displayCartList() {

        Connection conn = getConnection();
        ArrayList<CartDto> cartList = cartDao.displayCartList(conn);

        return cartList;

    }

    public int deleteCartProduct(int cartProductId) {

        Connection conn = getConnection();
        int result = cartDao.deleteCartProduct(conn, cartProductId);

        if(result > 0) commit(conn);
        else rollback(conn);

        return result;

    }


}
