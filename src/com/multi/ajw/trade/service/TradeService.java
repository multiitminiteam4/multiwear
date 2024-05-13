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
    public ArrayList<OrderList> order_selectAll(){//구매내역 조회
        ArrayList<OrderList> list = tradeDao.order_selectAll(conn);
        return list;
    }

    public void add() {//선택한 제품을 보상판매 신청 리스트에 추가
        tradeDao.add_trade(conn);
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
}
