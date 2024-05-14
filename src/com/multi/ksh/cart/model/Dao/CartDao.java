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

    // 쿼리 프로퍼티스를 사용하기 위한 변수 생성 (본인은 사용 안 함)
    //private Properties prop = null;

    // CartDao 기본 생성자
    public CartDao() {

//        try {
//            prop = new Properties();
//            prop.load(new FileReader("resources/query.properties"));
//            //  prop.loadFromXML(new FileInputStream("mapper/query.xml"));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    // shoppingcart 테이블에 있는 데이터를 CartDto타입의 ArrayList에 담아서 가져오는 메소드
    public ArrayList<CartDto> displayCartList(Connection conn, int id) {

        // CartDto타입의 ArrayList를 cartList라는 변수로 선언
        ArrayList<CartDto> cartList = null;

        // 실
        PreparedStatement ps = null;
        ResultSet rs = null; // Select 한후 결과값 받아올 객체

        String sql = "SELECT\n" +
                "\tA.PRODUCT_ID, \n" +
                "\tB.PRODUCT_NAME,\n" +
                "\tB.PRICE,\n" +
                "\tA.AMOUNT\n" +
                "FROM shoppingcart A\n" +
                "LEFT JOIN PRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID\n" +
                "WHERE id = ?";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            // 쿼리문 전송, 실행결과를 ResultSet 으로 받기
            rs = ps.executeQuery();

            // 받은결과값을 객체에 옮겨서 저장하기
            cartList = new ArrayList<CartDto>();

            while (rs.next()) {

                CartDto cartDto = new CartDto();

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

    public int displayPoint(Connection conn, int id) {

        int userPoint = 0;

        PreparedStatement ps = null; // 실행할 쿼리
        ResultSet rs = null;

        String sql = "SELECT point\n" +
                "FROM users\n" +
                "WHERE id = ?";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                userPoint = rs.getInt("point");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }

        return userPoint;

    }

    public int insertOrderList(Connection conn, int id, int payAmount, String address) {

        int orderId = 0;
        ResultSet rs = null;

        PreparedStatement ps = null;
        PreparedStatement ps2 = null;

        String sql = "INSERT INTO ORDERS VALUES (ORDERS_ID_SEQ.NEXTVAL, ?, ?, ?, sysdate)";
        String sql2 = "SELECT ORDERS_ID_SEQ.CURRVAL FROM DUAL";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2, payAmount);
            ps.setString(3, address);

            ps.executeUpdate();

            ps2 = conn.prepareStatement(sql2);
            rs = ps2.executeQuery();

            if(rs.next()){
                orderId = rs.getInt(1);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(ps);
        }

        return orderId;

    }

    public int deleteCartProduct(Connection conn, int id, int cartProductId) {

        int result = 0;

        PreparedStatement ps = null; // 실행할 쿼리

        String sql = "DELETE FROM SHOPPINGCART WHERE id = ? AND product_id = ?";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2, cartProductId);

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

    public int resetCart(Connection conn, int id) {

        int result = 0;

        PreparedStatement ps = null; // 실행할 쿼리

        String sql = "DELETE FROM SHOPPINGCART WHERE id = ?";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

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

    public int deductPoint(Connection conn, int id, int usedPoint) {

        int result = 0;

        PreparedStatement ps = null; // 실행할 쿼리

        String sql = "UPDATE users SET point = (point - ?) WHERE id = ?";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, usedPoint);
            ps.setInt(2, id);

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

    public int isShoppingcartEmpty(Connection conn, int id) {

        int shoppingcartRowCount = 0;

        PreparedStatement ps = null; // 실행할 쿼리
        ResultSet rs = null;

        String sql = "SELECT COUNT(*) AS count\n" +
                "FROM SHOPPINGCART \n" +
                "WHERE id = ?";

        try {

            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if(rs.next()) {
                shoppingcartRowCount = rs.getInt("count");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rs);
            close(ps);
        }

        return shoppingcartRowCount;

    }


    public int insertOrderProductList(Connection conn, int orderId, ArrayList<CartDto> cartList) {

        int result  = 0;

        PreparedStatement ps = null;

        String sql = "INSERT ALL";

        for(CartDto cartDto : cartList) {
            sql += " INTO ORDER_PRODUCT (order_id, product_id, amount, is_trade) VALUES (?, ?, ?, ?)";
        }
        sql += " SELECT * FROM DUAL";


        try {

            ps = conn.prepareStatement(sql);

            for(int i = 0; i < cartList.size(); i++) {

                CartDto cartDto = cartList.get(i);

                ps.setInt(1+i*4, orderId);
                ps.setInt(2+i*4, cartDto.getProductId());
                ps.setInt(3+i*4, cartDto.getAmount());
                ps.setString(4+i*4, "T");
            }

            result = ps.executeUpdate();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(ps);
        }

        return result;

    }
}
