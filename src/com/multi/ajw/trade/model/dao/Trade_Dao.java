package com.multi.ajw.trade.model.dao;

import com.multi.ajw.run.Trade_Run;
import com.multi.ajw.trade.model.dto.OrderList;
import com.multi.ajw.trade.model.dto.Productlist;
import com.multi.ajw.trade.model.dto.UserList;

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
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql= prop.getProperty("selectORDERLIST");
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(Trade_Run.auth));
            rset = stmt.executeQuery();

            list = new ArrayList<OrderList>();
            while (rset.next()) {
                OrderList l = new OrderList();
                l.setProduct_name(rset.getString("PRODUCT_NAME"));
                l.setAmount(rset.getInt("AMOUNT"));
                l.setT_price(rset.getInt("A.AMOUNT*B.PRICE"));
                l.setPrice(rset.getInt("PRICE"));
                l.setOrder_id(rset.getInt("ORDER_ID"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public void add_trade(Connection conn, ArrayList<OrderList> list) {//보상판매 리스트에 넣기, 상태 F로 변경
        Scanner sc = new Scanner(System.in);
        System.out.println("추가할 주문id 입력");
        int oi = sc.nextInt();
        sc.nextLine();
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        String sql= prop.getProperty("inputTRADELIST");
        String sql2 = prop.getProperty("findPRODUCT");
        String sql3 = prop.getProperty("changeVISIBLE");
        int rs = 0;
        Productlist pl = new Productlist();
        ResultSet rset=null;
        try {
            stmt = conn.prepareStatement(sql2);
            stmt.setInt(1,oi);
            stmt.setString(2, Trade_Run.auth);
            rset=stmt.executeQuery();
            while(rset.next()) {
                pl.setProduct_id(rset.getInt("ORDER_ID"));
            }
                stmt2 = conn.prepareStatement(sql);
                stmt2.setString(1, Trade_Run.auth);
                stmt2.setInt(2, pl.getProduct_id());//order_id
                rs = stmt2.executeUpdate();

            if (rs >= 1) {
                    conn.commit();
                }
            stmt3=conn.prepareStatement(sql3);
            stmt3.setInt(1,oi);
            rs=stmt3.executeUpdate();
            if(rs>=1){
                conn.commit();
            }

        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt2);
            close(stmt3);
            close(stmt);
        }
    }

    public ArrayList<UserList> user_selectAll(Connection conn) {// 보상판매 신청 유저 보기
        ArrayList<UserList> list=null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql= prop.getProperty("showTRADEUSER");
        try {
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();

            list = new ArrayList<UserList>();
            while (rset.next()) {
                UserList l = new UserList();
                l.setUser_id(rset.getString("ID"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public ArrayList<UserList> user_app_selectAll(Connection conn) {// 선택한 유저의 보상판매 신청 목록 보기
        ArrayList<UserList> list=null;
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql= prop.getProperty("showUSERAPP");
        try {
            System.out.println("\n==========================");
            System.out.println("조회할 유저를 선택해주세요");
            String user=sc.nextLine();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,Integer.parseInt(user));
            rset = stmt.executeQuery();

            list = new ArrayList<UserList>();
            while (rset.next()) {
                UserList l = new UserList();
                l.setTrade(rset.getInt("TRADE_IN_ID"));
                l.setProduct_id(rset.getInt("PRODUCT_ID"));
                l.setProduct_name(rset.getString("PRODUCT_NAME"));
                l.setIsapproved(rset.getString("is_approved"));
                l.setPrice(rset.getInt("C.PRICE*B.AMOUNT"));
                l.setDate(rset.getDate("APP_DATE"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public void judge(Connection conn) {//승인 및 거절
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        PreparedStatement stmt3 = null;
        PreparedStatement stmt4 = null;
        ResultSet rset = null;
        ResultSet rset2 = null;
        int rs;
        int point=0;
        String sql3 = prop.getProperty("changePOINT");
        String sql4 = prop.getProperty("loadPOINT");
        String sql;
        System.out.println("==========================");
        System.out.println("판단 할 보상판매 ID 선택");
        int trade_id=sc.nextInt();
        System.out.println("Y: 승인, 나머지: 거절");
        sc.nextLine();
        String judge=sc.nextLine();
        if(judge.toUpperCase().equals("Y")){
            sql= prop.getProperty("judgeAPPROVE_ACCEPT");
        }else{
            sql= prop.getProperty("judgeAPPROVE_REJECT");
        }
        String sql2 = prop.getProperty("getPRICE");

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,trade_id);
            rs = stmt.executeUpdate();

            if(rs>=1){
                conn.commit();
            }
            if(judge.toUpperCase().equals("Y")) {// 승인 했을 시에 유저의 잔여 포인트를 불러오고 추가 될 포인트를 더해서 다시 유저의 포인트에 저장
                stmt2 = conn.prepareStatement(sql2);
                stmt2.setInt(1, trade_id);
                rset = stmt2.executeQuery();
                while (rset.next()) {
                    price = rset.getInt("c.price*b.AMOUNT");
                    int id = rset.getInt("ID");
                    stmt4 = conn.prepareStatement(sql4);
                    stmt4.setInt(1,id);
                    rset2 = stmt4.executeQuery();
                    while (rset2.next()) {
                        point = rset2.getInt("POINT");
                        System.out.println("포인트가 "+(int)(price * 0.01)+"만큼 추가되었습니다.");
                        stmt3 = conn.prepareStatement(sql3);
                        stmt3.setDouble(1, point + price * 0.01);
                        stmt3.setInt(2, id);
                        rs = stmt3.executeUpdate();
                        if (rs >= 1) {
                            System.out.println("현재 포인트: "+point + (int)(price * 0.01));
                            conn.commit();
                        }
                    }
                }
            }

        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt2);
            close(stmt);
        }
    }

    public ArrayList<UserList> user_check_result(Connection conn) {//유저가 본인이 신청한 정보 보기
        ArrayList<UserList> list=null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql= prop.getProperty("showMYAPP");
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Trade_Run.auth);
            rset = stmt.executeQuery();

            list = new ArrayList<UserList>();
            while (rset.next()) {
                UserList l = new UserList();
                l.setTrade(rset.getInt("TRADE_IN_ID"));
                l.setProduct_id(rset.getInt("PRODUCT_ID"));
                l.setProduct_name(rset.getString("PRODUCT_NAME"));
                l.setIsapproved(rset.getString("is_approved"));
                l.setPrice(rset.getInt("C.PRICE*B.AMOUNT"));
                l.setDate(rset.getDate("APP_DATE"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public ArrayList<Productlist> show_product_list(Connection conn) {// 제품 리스트 조회
        ArrayList<Productlist> list=null;
        PreparedStatement stmt = null;
        ResultSet rset = null;
        String sql= prop.getProperty("showPRODUCTLIST");
        try {
            stmt = conn.prepareStatement(sql);
            rset = stmt.executeQuery();

            list = new ArrayList<Productlist>();
            while (rset.next()) {
                Productlist l = new Productlist();
                l.setProduct_id(rset.getInt("PRODUCT_ID"));
                l.setProduct_name(rset.getString("PRODUCT_NAME"));
                l.setPrice(rset.getInt("PRICE"));
                l.setCreated_at(rset.getDate("CREATED_AT"));
                list.add(l);
            }
        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(rset);
            close(stmt);
        }
        return list;
    }

    public void add_cart(Connection conn) {//장바구니 추가
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;
        String sql= prop.getProperty("inputCart");
        int rs = 0;

        try {
            System.out.println("장바구니에 추가할 제품id 입력");
            int pi=sc.nextInt();
            sc.nextLine();
            System.out.println("추가할 수량 입력");
            int amount=sc.nextInt();
            sc.nextLine();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Trade_Run.auth);
            stmt.setInt(2,pi);
            stmt.setInt(3,amount);
            rs=stmt.executeUpdate();
            if(rs>=1){
                conn.commit();
            }

        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(stmt);
        }
    }

    public void insert_product(Connection conn) {//제품 등록
        Productlist pl = new Productlist();
        Scanner sc = new Scanner(System.in);
        PreparedStatement stmt = null;
        String sql= prop.getProperty("inputPRODUCT");
        int rs = 0;

        try {
            System.out.println("새로 추가할 제품 이름 입력");
            pl.setProduct_name(sc.nextLine());
            System.out.println("새로 추가할 제품 가격 입력");
            pl.setPrice(sc.nextInt());
            sc.nextLine();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,pl.getProduct_name());
            stmt.setInt(2,pl.getPrice());
            rs=stmt.executeUpdate();
            if(rs>=1){
                conn.commit();
            }

        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(stmt);
        }
    }

    public void update_product(Connection conn) {// 제품 수정
        Scanner sc = new Scanner(System.in);
        Productlist pl = new Productlist();
        PreparedStatement stmt = null;
        String sql= prop.getProperty("updatePRODUCT");
        int rs = 0;
        try {
            System.out.println("변경 할 제품ID 입력");
            pl.setProduct_id(sc.nextInt());
            sc.nextLine();
            System.out.println("변경 될 제품 이름 입력");
            pl.setProduct_name(sc.nextLine());
            System.out.println("변경 될 제품 가격 입력");
            pl.setPrice(sc.nextInt());
            sc.nextLine();
            // 3. 쿼리문을 실행할 statement 객체 생성
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, pl.getProduct_name());
            stmt.setInt(2,pl.getPrice());
            stmt.setInt(3,pl.getProduct_id());
            rs=stmt.executeUpdate();
            if(rs>=1){
                conn.commit();
            }else{
                System.out.println("해당하는 id의 상품이 없습니다.");
            }

        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(stmt);
        }
    }

    public void delete_product(Connection conn) {//제품 삭제
        Scanner sc = new Scanner(System.in);
        Productlist pl = new Productlist();
        PreparedStatement stmt = null;
        String sql= prop.getProperty("deletePRODUCT");
        int rs = 0;
        try {
            System.out.println("삭제 할 제품ID 입력");
            pl.setProduct_id(sc.nextInt());
            sc.nextLine();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,pl.getProduct_id());
            rs=stmt.executeUpdate();
            if(rs>=1){
                conn.commit();
            }else {
                System.out.println("해당하는 id의 상품이 없습니다.");
            }

        } catch (
                SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            close(stmt);
        }
    }
}
