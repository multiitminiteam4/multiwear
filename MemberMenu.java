package multiwear.member.view;

import multiwear.member.controller.MemberController;
import multiwear.member.model.dto.MemberDto;

import java.util.Scanner;

public class MemberMenu {

    static MemberController memberController = new MemberController();
    static Scanner sc = new Scanner(System.in);
    static String currentId ;


    // 로그인
    public static boolean logIn() {
        System.out.println("ID를 입력하세요: ");
        String user_Id = sc.nextLine();

        System.out.println("비밀번호를 입력하세요: ");
        String password = sc.nextLine();

        boolean isSuccess= memberController.memberSearch(new MemberDto(user_Id, password));
        currentId = user_Id;
        return isSuccess;
    }


    //  회원 가입
    public static void signUp(){
        System.out.print("아이디: ");
        String user_Id = sc.nextLine();

        System.out.print("이름: ");
        String name = sc.nextLine();

        System.out.print("이메일: ");
        String email = sc.nextLine();

        System.out.print("비밀번호: ");
        String password = sc.nextLine();

        System.out.print("전화번호: ");
        String phone = sc.nextLine();

        memberController.memberJoin(new MemberDto(user_Id, name, email, password, phone));

    }


    

    public static void myPage() {
        boolean isValidInput = false;

        do {
            System.out.println("마이페이지입니다.");
            System.out.println("1. 회원정보수정");
            System.out.println("2. 구매내역조회");
            System.out.println("3. 보상판매조회");
            System.out.println("4. 회원 탈퇴");
            System.out.println("*. 메인으로 돌아가기");
            System.out.println("#. 프로그램 종료");

            String menu = sc.nextLine();
            sc.nextLine();
            switch (menu) {
                case "1":
                    UpdateMember();
                    break;
                case "2": // 구매내역조회
                    break;
                case "3": // 보상판매조회
                    break;
                case "4": // 회원 탈퇴
                    System.out.println("탈퇴하시겠습니까?");
                    System.out.println("1. YES");
                    System.out.println("2. NO");
                    sc.nextLine();


                    int  signOut = sc.nextInt();

                    if (signOut == 1) {
                        memberController.deleteMember(currentId);
                        System.exit(0);

                    } else if (signOut == 2) {
                        System.out.println(" 탈퇴 취소 하셨습니다.");
                    } else {
                        System.out.println("잘못입력하셨습니다.");
                    }
                    break;
                default:
                    System.out.println("잘못입력하셨습니다.");
                    break;
            }
        } while (!isValidInput);
    }

    private static void UpdateMember() {
    }

}
