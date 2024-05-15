package com.multi.ksh.cart.view;

import com.multi.ksh.cart.controller.CartController;
import com.multi.ksh.cart.model.Dto.CartDto;

import java.util.List;
import java.util.Scanner;

public class CartMenu {

    // 스캐너 객체 생성: 어디서든 쓸 수 있게 전역 변수로 생성
    private static Scanner sc = new Scanner(System.in);

    // CartController 객체 생성 (기본 생성자): : 어디서든 쓸 수 있게 전역 변수로 생성
    private CartController cartController = new CartController();

    // cartMenu 메소드 생성
    public void cartMenu(int id) {

        String choice;

        System.out.println("\n *************** 장바구니 조회 ***************");

        if(cartController.isShoppingcartEmpty(id) == 0) {

            do {
                System.out.println("\n             <장비구니가 비어있음>");
                System.out.println("\n*. 메인으로 돌아가기");
                System.out.println("#. 프로그램 종료");

                choice = sc.next();

                switch (choice) {

                    case "*":
                        // mainMenu() // 메인메뉴를 보여주는 메서드 호출
                        break;
                    case "#":
                        System.out.println("\n시스템을 종료합니다.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("잘못된 입력입니다. 다시 입력해 주세요.");

                }

            } while (true);

        }
        else {

            do {
                cartController.displayCartList(id);

                System.out.println("\n1. 담은 상품 구매");
                System.out.println("2. 담은 상품 삭제");
                System.out.println("*. 메인으로 돌아가기");
                System.out.println("#. 프로그램 종료");

                choice = sc.next();

                switch (choice) {

                    case "1":
                        purchase(id);
                        break;
                    case "2":
                        deleteCartProduct(id);
                        break;
                    case "*":
                        // mainMenu() // 메인메뉴를 보여주는 메서드 호출
                        break;
                    case "#":
                        System.out.println("\n시스템을 종료합니다.");
                        System.exit(0);
                        break;
                    default:
                        break;
                }

            } while (true);

        }

    } // cartMenu end


    public void displayCartList(List<CartDto> cartList) {

        System.out.println("\n               <담은 상품 목록>");

        int totalPrice = 0;

        for (CartDto cartDto : cartList) {
            System.out.println("상품 ID: " + cartDto.getProductId() + " / " + cartDto.getProductName() + " / 가격: " + cartDto.getPrice() + " / 수량: " + cartDto.getAmount());
            totalPrice += cartDto.getPrice() * cartDto.getAmount();
        }

        System.out.println(">> 상품 총 가격: " + totalPrice);

    }


    public void deleteCartProduct(int id) {

        int cartProductId;

        System.out.println("장바구니에서 삭제할 상품ID를 입력해 주세요.");
        cartProductId = sc.nextInt();

        cartController.deleteCartProduct(id, cartProductId);

        System.out.println("선택한 상품이 장바구니에서 삭제되었습니다.");

        cartMenu(id);

    }

    public void purchase(int id) {

        String address = "";
        int userPoint = cartController.displayPoint(id);
        int usedPoint = 0;
        int payType = 0;
        int payAmount = 0;
        String payChoice = "";

        System.out.println("배송지를 입력해 주세요.");
        address = sc.next();

        System.out.println(">> 잔여 포인트: " + userPoint);
        System.out.println("사용할 포인트를 입력해 주세요.");
        usedPoint = sc.nextInt();

        if(usedPoint > userPoint) {
            System.out.println("입력한 포인트가 잔여 포인트 보다 많습니다. 구매를 중단하고 장바구니로 돌아갑니다.");
            return;
        }

        System.out.println("결제 수단을 선택해 주세요.");
        System.out.println("1. 카드 결제");
        System.out.println("2. 무통장 입금");
        System.out.println("3. 네이버 페이 [5% 할인]");
        payType = sc.nextInt();

        payAmount = cartController.displayPayAmount(id, usedPoint, payType);
        System.out.println(">> 총 결제 금액: " + payAmount);
        System.out.println("결제하시겠습니까? (Y/N)");
        payChoice = sc.next();

        if(payChoice.equals("Y") || payChoice.equals("y")) {
            // 유저테이블 point에서 usepoint 업데이트

            System.out.println("결제가 완료되었습니다.");

            int orderId = cartController.insertOrderList(id, payAmount, address);

            cartController.insertOrderProductList(id, orderId);

            cartController.resetCart(id);

            cartController.deductPoint(id, usedPoint);


            cartMenu(id);

        }
        else if(payChoice.equals("N") || payChoice.equals("n")) {
            System.out.println("결제가 취소되었습니다.");
            cartMenu(id);
        }
        else{
            System.out.println("잘못된 입력입니다. 결제가 취소되었습니다.");
            cartMenu(id);
        }

    }



}
