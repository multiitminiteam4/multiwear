package com.multi.ajw.trade.service;

import com.multi.ajw.trade.model.dao.Trade_Dao;
import com.multi.ajw.trade.model.dto.OrderList;
import com.multi.ajw.trade.model.dto.Productlist;
import com.multi.ajw.trade.model.dto.UserList;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.multi.ajw.common.JDBCTemplate.getConnection;

public class TradeService {
    private final Trade_Dao tradeDao;
    Connection conn= getConnection();
    public TradeService() {
        tradeDao = new Trade_Dao();
    }
    public ArrayList<OrderList> order_selectAll(){//구매내역 조회
        ArrayList<OrderList> list = tradeDao.order_selectAll(conn);
        return list;
    }

    public void add(ArrayList<OrderList> list) {//선택한 제품을 보상판매 신청 리스트에 추가
        tradeDao.add_trade(conn,list);
    }

    public ArrayList<UserList> application_selectAll() {//보상판매를 신청한 유저 리스트 조회
        ArrayList<UserList> list = tradeDao.user_selectAll(conn);
        return list;
    }

    public ArrayList<UserList> app_list() {//신청한 유저를 입력해서 유저의 신청 리스트 조회
        ArrayList<UserList> list = tradeDao.user_app_selectAll(conn);
        return list;
    }


    public void judge() {//보상판매 승인 및 거절
        tradeDao.judge(conn);
    }

    public ArrayList<UserList> is_approved() {//유저가 본인이 신청한 결과 확인
        ArrayList<UserList> list = tradeDao.user_check_result(conn);
        return list;
    }

    public ArrayList<Productlist> show_product_list() {
        ArrayList<Productlist> list = tradeDao.show_product_list(conn);
        return list;//상품 리스트 조회 및 장바구니 담기
    }

    public void add_cart() {//장바구니에 추가
        tradeDao.add_cart(conn);
    }

    public void insert_product() {// 상품 등록
        tradeDao.insert_product(conn);
    }

    public void update_product() {//상품 업데이트
        tradeDao.update_product(conn);
    }

    public void delete_product() {//상품 삭제
        tradeDao.delete_product(conn);
    }

    public List<UserList> getUserList() {
        return tradeDao.getUserList(conn);
    }

    public List<UserList> getTradeInList() {
        return tradeDao.getTradeInList(conn);
    }

}
