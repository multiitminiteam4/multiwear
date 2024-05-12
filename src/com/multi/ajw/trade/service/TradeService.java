package com.multi.ajw.trade.service;

import com.multi.ajw.trade.model.dao.Trade_Dao;
import com.multi.ajw.trade.model.dto.OrderList;
import com.multi.ajw.trade.model.dto.UserList;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.Connection;
import java.util.ArrayList;

import static com.multi.ajw.common.JDBCTemplate.getConnection;

public class TradeService {
    private final Trade_Dao tradeDao;
    Connection conn = getConnection();
    public TradeService(){
        tradeDao = new Trade_Dao();
    }
    public ArrayList<OrderList> order_selectAll(){
        ArrayList<OrderList> list = tradeDao.order_selectAll(conn);
        return list;
    }

    public void add() {
        tradeDao.add_trade(conn);
    }

    public ArrayList<UserList> application_selectAll() {
        ArrayList<UserList> list = tradeDao.user_selectAll(conn);
        return list;
    }

    public ArrayList<UserList> app_list() {//신청한 유저를 입력해서 유저의 신청 리스트 조회
        ArrayList<UserList> list = tradeDao.user_app_selectAll(conn);
        return list;
    }


    public void judge() {
        tradeDao.judge(conn);
    }

    public ArrayList<UserList> is_approved() {
        ArrayList<UserList> list = tradeDao.user_check_result(conn);
        return list;
    }
}
