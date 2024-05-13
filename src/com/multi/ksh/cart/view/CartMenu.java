package com.multi.ksh.cart.view;

import com.multi.ksh.cart.controller.CartController;
import com.multi.ksh.cart.model.Dto.CartDto;

import java.util.List;
import java.util.Scanner;

public class CartMenu {

    // 스캐너 객체 생성
    private static Scanner sc = new Scanner(System.in);

    // CartController 객체 생성 (기본 생성자)
    private CartController cartController = new CartController();

    // cartMenu 메소드 생성
    public void cartMenu() {

        String choice;

        System.out.println("\n ********** 장바구니 조회 **********");

        // 담은 상품과 수량을 출력
        cartController.displayCartList();

        System.out.println("1. 담은 상품 구매");
        System.out.println("2. 담은 상품 삭제");
        System.out.println("*. 메인으로 돌아가기");
        System.out.println("#. 프로그램 종료");

        choice = sc.next();

        switch (choice) {

            case "1":
                purchase();
                break;
            case "2":
                deleteCartProduct();
                break;
        }

    } // cartMenu end

    public void displayCartList(List<CartDto> cartList) {

        System.out.println("<담은 상품 목록>");

        int totalPrice = 0;

        for (CartDto cartDto : cartList) {
            System.out.println("상품 ID: " +cartDto.getProductId() + ". " + cartDto.getProductName() + " / 가격: " + cartDto.getPrice() + " / 수량: " + cartDto.getAmount());
            totalPrice += cartDto.getPrice() * cartDto.getAmount();
        }

        System.out.println(">> 상품 총 가격: " + totalPrice);

    }


    public void deleteCartProduct() {

        int cartProductId;

        System.out.println("장바구니에서 삭제할 상품ID를 입력해 주세요.");
        cartProductId = sc.nextInt();

        cartController.deleteCartProduct(cartProductId);

    }

    public void purchase() {

        String address;
        int userpoint = 5000; //유저 DB에서 가져오기
        int usePoint = 0;
        int payType;
        int payAmount = 0;
        String payChoice;

        System.out.println("배송지를 입력해 주세요.");
        address = sc.next();

        System.out.println("<잔여 포인트>\n" + userpoint);
        System.out.println("사용할 포인트를 입력해 주세요.");
        usePoint = sc.nextInt();

        System.out.println("결제 수단을 선택해 주세요.");
        System.out.println("1. 카드 결제");
        System.out.println("2. 무통장 입금");
        System.out.println("3. 네이버 페이 [5% 할인]");
        payType = sc.nextInt();

        // 장바구니 합산 - 포인트 사용 - 네이버 페이 할인 = 컨트롤러에서 총 결제 금액 구하기
        System.out.println(">> 총 결제 금액: " + payAmount);
        System.out.println("결제하시겠습니까? (Y/N)");
        payChoice = sc.next();

        if(payChoice.equals("Y") || payChoice.equals("y")) {
            // 구매내역 db insert
            // 유저테이블 point에서 usepoint 업데이트
            // 장바구니 물품 다 없애기
            System.out.println("결제가 완료되었습니다.");
        }
        else{
            System.out.println("결제가 취소되었습니다.");
            cartMenu();
        }

    }



}
