package com.multi.ajw.trade.model.dao;

import com.multi.ajw.run.Trade_Run;
import com.multi.ajw.trade.model.dto.OrderList;
import com.multi.ajw.trade.model.dto.UserList;
import com.multi.ajw.trade.view.Trade_add;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import static com.multi.ajw.common.JDBCTemplate.close;

public class Trade_Dao {
    private Properties prop = null;
    int price;
    public Trade_Dao() {

        try {
            prop = new Properties();
            prop.load(new FileReader("resources/query.properties"));
            //  prop.loadFromXML(new FileInputStream("mapper/query.xml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<OrderList> order_selectAll(Connection conn) {//보상판매 신청 가능 내역 조회
        ArrayList<OrderList> list= null;
        PreparedStatement stmt = null;// 실행할 쿼리
        ResultSet rset = null;// Select 한후 결과값 받아올 객체
        String sql= prop.getProperty("selectORDERLIST");
        try {
            // 3. 쿼리문을 실행할 statement 객체 생성
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Trade_Run.auth);
// 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
            rset = stmt.executeQuery();

// 5. 받은결과값을 객체에 옮겨서 저장하기
            list = new ArrayList<OrderList>();
            while (rset.next()) {
                System.out.println("왜");
                OrderList l = new OrderList();
                l.setOrder_id(rset.getInt("ORDER_ID"));
                l.setUser_id(rset.getString("USER_ID"));
                l.setProduct_id(rset.getInt("PRODUCT_ID"));
                l.setProduct_name(rset.getString("PRODUCT_NAME"));
                l.setPrice(rset.getInt("PRICE"));

                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public void add_trade(Connection conn) {//보상판매 리스트에 넣기
        System.out.println("추가할 제품id 입력");
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;// 실행할 쿼리
        String sql= prop.getProperty("inputTRADELIST");
        String sql2 = prop.getProperty("findPRICE");
        String sql3 = prop.getProperty("changeVISIBLE");
        int rs = 0;
        int rs2 = 0;
        ResultSet rset=null;
        try {
            int pi=sc.nextInt();
            stmt2 = conn.prepareStatement(sql2);
            stmt2.setInt(1,pi);
            rset = stmt2.executeQuery();
            while(rset.next()){
                price=rset.getInt("PRICE");
            }
            // 3. 쿼리문을 실행할 statement 객체 생성
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Trade_Run.auth);
            stmt.setInt(2,pi);
            stmt.setInt(3,price);
            rs=stmt.executeUpdate();
            if(rs>=1){
                conn.commit();
            }
            stmt3 = conn.prepareStatement(sql3);
            stmt3.setInt(1,pi);
            rs2 = stmt3.executeUpdate();
            if(rs2>=1){
                conn.commit();
            }

        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(stmt3);
            close(stmt2);
            close(rset);
            close(stmt);
        }
    }

    public ArrayList<UserList> user_selectAll(Connection conn) {// 보상판매 신청 유저 보기
        ArrayList<UserList> list=null;
        PreparedStatement stmt = null;// 실행할 쿼리
        ResultSet rset = null;// Select 한후 결과값 받아올 객체
        String sql= prop.getProperty("showTRADEUSER");
        try {
            // 3. 쿼리문을 실행할 statement 객체 생성
            stmt = conn.prepareStatement(sql);
// 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
            rset = stmt.executeQuery();

// 5. 받은결과값을 객체에 옮겨서 저장하기
            list = new ArrayList<UserList>();
            while (rset.next()) {
                UserList l = new UserList();
                l.setUser_id(rset.getString("USER_ID"));
//                l.setProduct_id(rset.getInt("PRODUCT_ID"));
//                l.setIsapproved(rset.getString("ISAPPROVED"));
//                l.setPrice(rset.getInt("PRICE"));
//                l.setDate(rset.getDate("APP_DATE"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public ArrayList<UserList> user_app_selectAll(Connection conn) {// 선택한 유저의 보상판매 신청 목록 보기
        ArrayList<UserList> list=null;
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;// 실행할 쿼리
        ResultSet rset = null;// Select 한후 결과값 받아올 객체
        String sql= prop.getProperty("showUSERAPP");
        try {
            System.out.println("\n==========================");
            System.out.println("조회할 유저를 선택해주세요");
            String user=sc.nextLine();
            // 3. 쿼리문을 실행할 statement 객체 생성
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,user);
// 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
            rset = stmt.executeQuery();

// 5. 받은결과값을 객체에 옮겨서 저장하기
            list = new ArrayList<UserList>();
            while (rset.next()) {
                UserList l = new UserList();
                l.setUser_id(rset.getString("USER_ID"));
                l.setProduct_id(rset.getInt("PRODUCT_ID"));
                l.setIsapproved(rset.getString("ISAPPROVED"));
                l.setPrice(rset.getInt("PRICE"));
                l.setDate(rset.getDate("APP_DATE"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public void judge(Connection conn) {//승인 및 거절
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;// 실행할 쿼리
        ResultSet rset = null;// Select 한후 결과값 받아올 객체
        int rs;
        String sql;
        System.out.println("==========================");
        System.out.println("판단 할 제품ID 선택");
        int product_id=sc.nextInt();
        System.out.println("Y: 승인, 나머지: 거절");
        sc.nextLine();
        String judge=sc.nextLine();
        if(judge.toUpperCase().equals("Y")){
            sql= prop.getProperty("judgeAPPROVE_ACCEPT");
        }else{
            sql= prop.getProperty("judgeAPPROVE_REJECT");
        }
        try {
            // 3. 쿼리문을 실행할 statement 객체 생성
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,product_id);
// 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
            rs = stmt.executeUpdate();

// 5. 받은결과값을 객체에 옮겨서 저장하기
            if(rs>=1){
                conn.commit();
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(rset);
            close(stmt);
        }
    }

    public ArrayList<UserList> user_check_result(Connection conn) {//유저가 본인이 신청한 정보 보기
        ArrayList<UserList> list=null;
        PreparedStatement stmt = null;// 실행할 쿼리
        ResultSet rset = null;// Select 한후 결과값 받아올 객체
        String sql= prop.getProperty("checkUSERAPP");
        try {
            // 3. 쿼리문을 실행할 statement 객체 생성
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Trade_Run.auth);
// 4.쿼리문 전송, 실행결과를 ResultSet 으로 받기
            rset = stmt.executeQuery();

// 5. 받은결과값을 객체에 옮겨서 저장하기
            list = new ArrayList<UserList>();
            while (rset.next()) {
                UserList l = new UserList();
                l.setUser_id(rset.getString("USER_ID"));
                l.setProduct_id(rset.getInt("PRODUCT_ID"));
                l.setIsapproved(rset.getString("ISAPPROVED"));
                l.setPrice(rset.getInt("PRICE"));
                l.setDate(rset.getDate("APP_DATE"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //   throw new MemberException("selectAll 에러 : " + e.getMessage());
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }
}
