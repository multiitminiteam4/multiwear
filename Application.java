package multiwear.run;

import multiwear.member.view.MemberMenu;

import java.util.Scanner;

public class Application {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n*******초기메뉴********");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("번호 입력: ");

            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("로그인");
                    if(MemberMenu.logIn()){
                        System.out.println("로그인이 완료되었습니다.");
                        System.out.println("\n*******메인메뉴********");
                        System.out.println("1. 상품조회");
                        System.out.println("2. 장바구니 조회");
                        System.out.println("3. 보상판매");
                        System.out.println("4. 마이페이지");
                        System.out.println("#. 프로그램 종료");

                        System.out.println("번호 입력: ");


                        switch (scanner.nextLine()) {
                            case "1":
                                System.out.println("상품조회");
                                break;
                            case "2":
                                System.out.println("장바구니 조회");
                                break;
                            case "3":
                                System.out.println("보상판매");
                                break;
                            case "4":
                                System.out.println("마이페이지");
                                MemberMenu.myPage();
                                break;
                            case "#":
                                System.out.println("프로그램 종료");
                                break;
                            default:
                                System.out.println("잘못된 입력입니다.");
                                break;
                        }
                        break;
                    }


                case "2":
                    System.out.println("회원가입");
                    MemberMenu.signUp();
                    break;
                default:
                    System.out.println("잘못된 입력입니다.");
                    break;
            }
        }
    }

