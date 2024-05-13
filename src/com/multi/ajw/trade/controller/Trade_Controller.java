package com.multi.ajw.trade.controller;

import com.multi.ajw.trade.model.dao.Trade_Dao;
import com.multi.ajw.trade.model.dto.OrderList;
import com.multi.ajw.trade.model.dto.Productlist;
import com.multi.ajw.trade.model.dto.UserList;
import com.multi.ajw.trade.service.TradeService;
import com.multi.ajw.trade.view.Trade_add;

import java.util.ArrayList;
import java.util.Scanner;

public class Trade_Controller {
    private static TradeService tradeService = new TradeService();
    public static void trade_regist() {//구매내역 조회 후 보상판매 신청
        Trade_add add = new Trade_add();
        //구매 목록 리스트 후 선택, 선택한 번호 등록
        ArrayList<OrderList> list;
        list = tradeService.order_selectAll();
        if(!list.isEmpty()){
            add.displayOrderList(list);
            tradeService.add();
        }else{
            System.out.println("신청 목록이 없습니다.");
        }
    }

    public static void show_user_list() {//보상판매 신청 유저 조회 및 승인|거절
        Trade_add add = new Trade_add();
        ArrayList<UserList> list;
        ArrayList<UserList> app_list;
        list= tradeService.application_selectAll();
        if(!list.isEmpty()){
            add.displayUSERList(list);
            app_list=tradeService.app_list();
            if(!app_list.isEmpty()){
                add.displayAPPList(app_list);
                tradeService.judge();
            }
        }else{
            System.out.println("신청 목록이 없습니다.");
        }
    }

    public static void check_trade() {//유저가 본인이 신청한 결과 확인
        Trade_add add = new Trade_add();
        //구매 목록 리스트 후 선택, 선택한 번호 등록
        ArrayList<UserList> list;
        list = tradeService.is_approved();
        if(!list.isEmpty()){
            add.displayAPPList(list);
        }else{
            System.out.println("신청 목록이 없습니다.");
        }
    }

    public static void show_product_list() {//상품 조회
        Trade_add add = new Trade_add();
        ArrayList<Productlist> list;
        list = tradeService.show_product_list();
        if(!list.isEmpty()){
            add.displayProductList(list);
        }
    }

    public static void input_cart() { //장바구니 담기
        tradeService.add_cart();

    }

    public static void insert_product() {//상품 등록
        tradeService.insert_product();
    }

    public static void update_product() {//상품 업데이트
        tradeService.update_product();
    }

    public static void delete_product() {//상품 삭제
        tradeService.delete_product();
    }
}
