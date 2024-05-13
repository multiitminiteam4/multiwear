package com.multi.ksh.cart.model.Dao;

import com.multi.ksh.cart.model.Dto.CartDto;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import static com.multi.ksh.common.JDBCTemplate.close;

public class CartDao {

    private Properties prop = null;

    // CartDao 생성자
    public CartDao() {

        try {
            prop = new Properties();
            prop.load(new FileReader("resources/query.properties"));
            //  prop.loadFromXML(new FileInputStream("mapper/query.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<CartDto> displayCartList(Connection conn) {

        ArrayList<CartDto> cartList = null;

        PreparedStatement ps = null; // 실행할 쿼리
        ResultSet rs = null; // Select 한후 결과값 받아올 객체

        String sql = "SELECT\n" +
                "\tA.PRODUCT_ID, \n" +
                "\tB.PRODUCT_NAME,\n" +
                "\tB.PRICE,\n" +
                "\tA.AMOUNT\n" +
                "FROM shoppingcart A\n" +
                "LEFT JOIN PRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID";
        // String sql = prop.getProperty("select");

        try {

            // 3. 쿼리문을 실행할 statement 객체 생성
            ps = conn.prepareStatement(sql);

            // 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
            rs = ps.executeQuery();

            // 5. 받은결과값을 객체에 옮겨서 저장하기
            cartList = new ArrayList<CartDto>();

            while (rs.next()) {

                CartDto cartDto = new CartDto();

                // cartDto.setId(rs.getInt("id"));
                cartDto.setProductId(rs.getInt("product_id"));
                cartDto.setAmount(rs.getInt("amount"));
                cartDto.setProductName(rs.getString("product_name"));
                cartDto.setPrice(rs.getInt("price"));

                cartList.add(cartDto);

            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(rs);
            close(ps);
        }
        return cartList;
    }

    public int deleteCartProduct(Connection conn, int cartProductId) {

        int result = 0;

        PreparedStatement ps = null; // 실행할 쿼리

        String sql = "DELETE FROM SHOPPINGCART WHERE product_id = ?";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, cartProductId);

            result = ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(ps);
        }

        return result;

    }



}
