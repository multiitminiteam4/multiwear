package com.multi.ajw.trade.view;

import com.multi.ajw.run.Trade_Run;
import com.multi.ajw.trade.controller.Trade_Controller;
import com.multi.ajw.trade.model.dto.OrderList;
import com.multi.ajw.trade.model.dto.UserList;
import jdk.swing.interop.SwingInterOpUtils;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trade_add {
    private static Scanner sc = new Scanner(System.in);
    Trade_Controller tradeController = new Trade_Controller();
    public void trade_root() {
        String choice;
        do {
            System.out.println("\n*******관리자 메뉴********");
            System.out.println("1. 상품 관리");
            System.out.println("2. 회원 정보 조회");
            System.out.println("3. 보상 판매 심사");// select, update
            System.out.println("#. 프로그램 종료");// 종료
            System.out.println("번호선택 : ");

            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("상품 관리");
                    break;
                case "2":
                    System.out.println("회원 정보 조회");
                    break;
                case "3":
                    System.out.println("보상 판매 심사");
                    Trade_Controller.show_user_list();
                    break;
                case "#":
                    System.out.println("정말로 끝내시겠습니까??(y/n)");
                    if ('y' == sc.next().toLowerCase().charAt(0)) {
                        return;
                    }

                    break;

                default:
                    System.out.println("번호를 잘못입력하였습니다.");
            }

        } while (true);
    }

    public void trade_user() {
        String choice;
        do {
            System.out.println("\n*******"+ Trade_Run.auth+"님의 메뉴********");
            System.out.println("1. 상품 조회");
            System.out.println("2. 장바구니 조회");
            System.out.println("3. 보상 판매 등록");// update
            System.out.println("4. 마이페이지");
            System.out.println("#. 프로그램 종료");// 종료
            System.out.println("번호선택 : ");

            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("1. 상품 조회");
                    break;
                case "2":
                    System.out.println("2. 장바구니 조회");
                    break;
                case "3":
                    System.out.println("3. 보상 판매 등록");// update
                    Trade_Controller.trade_regist();
                    break;
                case "4":
                    System.out.println("4. 마이페이지");
                    System.out.println("1. 보상 판매 신청 목록");
                    if(sc.nextLine().equals("1")) {
                        Trade_Controller.check_trade();
                    }
                    break;
                case "#":
                    System.out.println("정말로 끝내시겠습니까??(y/n)");
                    if ('y' == sc.next().toLowerCase().charAt(0)) {
                        return;
                    }

                    break;

                default:
                    System.out.println("번호를 잘못입력하였습니다.");
            }

        } while (true);
    }

    public String auth() {
        System.out.println("1. root");
        System.out.println("2. user");
        return sc.nextLine();
    }
    public void displayOrderList(List<OrderList> list) {
        System.out.println("\n조회된 전체 정보는 다음과 같습니다.");
        System.out.println("\n주문id\t유저id\t제품id\t제품명\t가격");
        System.out.println("----------------------------------------------------------");

        for(OrderList l: list) {
            System.out.println(l);
        }

    }

    public void displayUSERList(ArrayList<UserList> list) {
        System.out.print("신청한 유저: |");
        for(UserList l: list) {
            System.out.print(l.list()+"|");
        }
    }
    public void displayAPPList(ArrayList<UserList> list){
        System.out.println("\n조회된 전체 정보는 다음과 같습니다.");
        System.out.println("\n유저id\t제품id\t승인여부\t가격\t신청날짜");
        System.out.println("----------------------------------------------------------");
        for(UserList l : list){
            System.out.println(l);
        }
    }
}
