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

    public ArrayList<CartDto> displayCartList(int id) {

        Connection conn = getConnection();
        ArrayList<CartDto> cartList = cartDao.displayCartList(conn, id);

        return cartList;

    }

    public int displayPoint(int id) {

        Connection conn = getConnection();
        int userPoint = cartDao.displayPoint(conn, id);

        return userPoint;

    }

    public int insertOrderList(int id, int payAmount, String address) {

        Connection conn = getConnection();
        int orderId = cartDao.insertOrderList(conn, id, payAmount, address);

        if(orderId > 0) commit(conn);
        else rollback(conn);

        return orderId;

    }

    public int deleteCartProduct(int id, int cartProductId) {

        Connection conn = getConnection();
        int result = cartDao.deleteCartProduct(conn, id, cartProductId);

        if(result > 0) commit(conn);
        else rollback(conn);

        return result;

    }

    public int resetCart(int id) {

        Connection conn = getConnection();

        int result = cartDao.resetCart(conn, id);

        if(result > 0) commit(conn);
        else rollback(conn);

        return result;

    }

    public int deductPoint(int id, int usedPoint) {

        Connection conn = getConnection();

        int result = cartDao.deductPoint(conn, id, usedPoint);

        if(result > 0) commit(conn);
        else rollback(conn);

        return result;

    }

    public int isShoppingcartEmpty(int id) {

        Connection conn = getConnection();

        int shoppingcartRowCount = cartDao.isShoppingcartEmpty(conn, id);

        return shoppingcartRowCount;

    }

    public void insertOrderProductList(int orderId, ArrayList<CartDto> cartList) {

        Connection conn = getConnection();

        int result = cartDao.insertOrderProductList(conn, orderId, cartList);

        if(result > 0) commit(conn);
        else rollback(conn);

    }
}
