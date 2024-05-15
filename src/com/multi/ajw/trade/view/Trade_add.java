package com.multi.ajw.trade.view;

import com.multi.ajw.run.Trade_Run;
import com.multi.ajw.trade.controller.Trade_Controller;
import com.multi.ajw.trade.model.dto.OrderList;
import com.multi.ajw.trade.model.dto.Productlist;
import com.multi.ajw.trade.model.dto.UserList;
import com.multi.ksh.cart.view.CartMenu;
import config.OracleSetting;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trade_add {
    private static Scanner sc = new Scanner(System.in);
    private String currentUserId;




    public void trade_root() { //관리자 메뉴
        String choice;
        do {
            System.out.println("\n*******관리자 메뉴********");
            System.out.println("1. 상품 관리");
            System.out.println("2. 회원 정보 조회");
            System.out.println("3. 보상 판매 심사");
            System.out.println("#. 프로그램 종료");
            System.out.println("번호선택 : ");

            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("1. 전체 상품 조회");
                    System.out.println("2. 상품 등록");
                    System.out.println("3. 상품 정보 업데이트");
                    System.out.println("4. 상품 삭제");
                    String r_choice=sc.nextLine();
                    switch (r_choice){
                        case "1":
                            Trade_Controller.show_product_list();
                            break;
                        case "2":
                            Trade_Controller.insert_product();
                            break;
                        case "3":
                            Trade_Controller.update_product();
                            break;
                        case "4":
                            Trade_Controller.delete_product();
                            break;

                        default:
                            System.out.println("번호를 잘못입력하였습니다.");
                    }
                    break;
                case "2":
                    System.out.println("회원 정보 조회");
                    memberInfoInquiry();
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



    public void memberInfoInquiry() {
        List<UserList> userList = Trade_Controller.getUserList();
        displayUserList(userList);
    }

    private void displayUserList(List<UserList> userList) {
        System.out.println("\n------- 회원 정보 -------");
        System.out.println("사용자 ID\t가입일");
        System.out.println("-------------------------");
        for (UserList user : userList) {
            System.out.printf("%s\t%s\n", user.getUser_id(), user.getDate());
        }
    }
    private void compensationSalesReview() {
        List<UserList> tradeInList = Trade_Controller.getTradeInList();
        displayTradeInList(tradeInList);
    }

    private void displayTradeInList(List<UserList> tradeInList) {
        System.out.println("\n------- 보상 판매 심사 -------");
        System.out.println("보상판매ID\t사용자ID\t제품ID\t제품명\t승인여부\t가격\t신청날짜");
        System.out.println("-------------------------------------------------------------------------");
        for (UserList tradeIn : tradeInList) {
            System.out.printf("%d\t%s\t%d\t%s\t%s\t%d\t%s\n",
                    tradeIn.getTrade(), tradeIn.getUser_id(), tradeIn.getProduct_id(),
                    tradeIn.getProduct_name(), tradeIn.getIsapproved(), tradeIn.getPrice(),
                    tradeIn.getDate());
        }
    }

    public int displayMemberInfoMenu() {
        System.out.println("----- 회원 정보 조회 -----");

        // users 테이블에서 회원 정보 조회
        List<UserList> userList = Trade_Controller.getUserList();

        for (UserList user : userList) {
            System.out.printf("%s - 가입일시: %s\n", user.getUser_id(), user.getDate());
        }

        System.out.println("0. 뒤로 가기");
        System.out.print("선택: ");
        return Integer.parseInt(sc.nextLine());
    }



    public void trade_user() {//유저 메뉴
        String choice;
        do {
            System.out.println("\n*******"+ Trade_Run.auth+"님의 메뉴********");
            System.out.println("1. 상품 조회 및 장바구니 담기");
            System.out.println("2. 장바구니 조회");
            System.out.println("3. 보상 판매 등록");// update
            System.out.println("4. 마이페이지");
            System.out.println("#. 프로그램 종료");// 종료
            System.out.println("번호선택 : ");

            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("1. 상품 조회 및 장바구니 담기");
                    Trade_Controller.show_product_list();
                    Trade_Controller.input_cart();
                    break;
                case "2":
                    System.out.println("2. 장바구니 조회");
                    int id = 1; // user id 로그인 시 받아오기 지금은 일단 1
                    new CartMenu().cartMenu(id);
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
    public void displayOrderList(List<OrderList> list) {//주문내역조회(유저기능)
        System.out.println("\n조회된 전체 정보는 다음과 같습니다.");
        System.out.println("\n주문id\t제품명\t수량\t가격\t총가격");
        System.out.println("----------------------------------------------------------");

        for(OrderList l: list) {
            System.out.println(l);
        }

    }

    public void displayUSERList(ArrayList<UserList> list) {//보상판매 신청 유저 조회(관리자기능)
        System.out.print("신청한 유저: |");
        for(UserList l: list) {
            System.out.print(l.list()+"|");
        }
    }
    public void displayAPPList(ArrayList<UserList> list){//선택한 유저의 보상판매 신청 리스트(관리자,유저 둘다 사용)
        System.out.println("\n조회된 전체 정보는 다음과 같습니다.");
        System.out.println("\n보상판매id\t제품id\t제품이름\t승인여부\t가격\t신청날짜");
        System.out.println("----------------------------------------------------------");
        for(UserList l : list){
            System.out.println(l);
        }
    }

    public void displayProductList(ArrayList<Productlist> list) {
        System.out.println("\n조회된 전체 정보는 다음과 같습니다.");
        System.out.println("\n제품id\t제품이름\t제품가격\t등록날짜");
        System.out.println("----------------------------------------------------------");
        for(Productlist l : list){
            System.out.println(l);
        }
    }
}
