import config.*;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{

        Connection connection = OracleSetting.getConnection();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            int mainChoice = displayMainMenu(scanner);

            if (mainChoice == 1) {
                productManagement(connection, scanner);
            } else if (mainChoice == 2) {
                memberManagement(connection, scanner);
            } else if (mainChoice == 3) {
                break;
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        }

        connection.close();


    }

    public static int displayMainMenu(Scanner scanner) {
        System.out.println("----- 메인 메뉴 -----");
        System.out.println("1. 제품 관리");
        System.out.println("2. 회원 관리");
        System.out.println("3. 뒤로 가기");
        System.out.print("선택: ");
        return scanner.nextInt();
    }

    public static void productManagement(Connection connection, Scanner scanner) {
        // 제품 관리 모듈 구현
        // ...
    }

    public static void memberManagement(Connection connection, Scanner scanner) throws SQLException {
        while (true) {
            int memberChoice = displayMemberManagementMenu(scanner);

            if (memberChoice == 1) {
                memberInfoInquiry(connection, scanner);
            } else if (memberChoice == 2) {
                compensationSalesReview(connection, scanner);
            } else {
                break;
            }
        }
    }

    public static int displayMemberManagementMenu(Scanner scanner) {
        System.out.println("----- 회원 관리 -----");
        System.out.println("1. 회원 정보 조회");
        System.out.println("2. 보상 판매 심사");
        System.out.print("선택: ");
        return scanner.nextInt();
    }

    public static void memberInfoInquiry(Connection connection, Scanner scanner) throws SQLException {
        while (true) {
            int memberInfoChoice = displayMemberInfoMenu(connection, scanner);

            if (memberInfoChoice == 0) {
                break;
            } else {
                // 선택한 회원의 상세 정보 조회 로직 추가
                displayMemberDetailInfo(connection, scanner, memberInfoChoice);
            }
        }
    }

    public static int displayMemberInfoMenu(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("----- 회원 정보 조회 -----");

        String query = "SELECT * FROM users";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userId = resultSet.getString("user_id");
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                int point = resultSet.getInt("point");
                Date createdAt = resultSet.getDate("created_at");

                System.out.printf("%d. %s (%s) - 가입일시: %s\n", id, userName, userId, createdAt);
            }
        }

        System.out.println("0. 뒤로 가기");
        System.out.print("선택: ");
        return scanner.nextInt();
    }

    public static void displayMemberDetailInfo(Connection connection, Scanner scanner, int memberId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("email");
                Date createdAt = resultSet.getDate("created_at");

                System.out.println("----- 회원 상세 정보 -----");
                System.out.println("아이디: " + userId);
                System.out.println("이름: " + userName);
                System.out.println("이메일: " + email);
                System.out.println("가입일시: " + createdAt);

                // 주문 내역 조회 로직 추가
                displayMemberOrderHistory(connection, memberId);
            } else {
                System.out.println("해당 회원을 찾을 수 없습니다.");
            }
        }

        System.out.println("0. 뒤로 가기");
        System.out.print("선택: ");
        int choice = scanner.nextInt();

        if (choice == 0) {
            return;
        }
    }

    public static void displayMemberOrderHistory(Connection connection, int memberId) throws SQLException {
        String query = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("주문 내역:");
            while (resultSet.next()) {
                Date orderedAt = resultSet.getDate("ordered_at");
                // 주문한 제품 정보 조회 로직 추가
                String products = getOrderedProducts(connection, resultSet.getInt("order_id"));
                System.out.println(orderedAt + " - " + products);
            }
        }
    }
//    public static void displayMemberDetailInfo(Connection connection, Scanner scanner, int memberId) throws SQLException {
//        String query = "SELECT * FROM users WHERE id = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, memberId);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                String userId = resultSet.getString("user_id");
//                String userName = resultSet.getString("user_name");
//                String email = resultSet.getString("email");
//                Date createdAt = resultSet.getDate("created_at");
//
//                System.out.println("----- 회원 상세 정보 -----");
//                System.out.println("아이디: " + userId);
//                System.out.println("이름: " + userName);
//                System.out.println("이메일: " + email);
//                System.out.println("가입일시: " + createdAt);
//
//                // 주문 내역 조회 로직 추가
//                displayMemberOrderHistory(connection, memberId);
//            } else {
//                System.out.println("해당 회원을 찾을 수 없습니다.");
//            }
//        }
//
//        System.out.println("0. 뒤로 가기");
//        System.out.print("선택: ");
//        int choice = scanner.nextInt();
//
//        if (choice == 0) {
//            return;
//        }
//    }
    public static String getOrderedProducts(Connection connection, int orderId) throws SQLException {
        String query = "SELECT p.product_name FROM order_product op " +
                "JOIN product p ON op.product_id = p.product_id " +
                "WHERE op.order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder products = new StringBuilder();
            while (resultSet.next()) {
                products.append(resultSet.getString("product_name")).append(", ");
            }
            if (products.length() > 0) {
                products.setLength(products.length() - 2);
            }
            return products.toString();
        }
    }

    public static void memberDetailInfo(Connection connection, Scanner scanner) {
        // 회원 상세 정보 조회 모듈 구현
        // ...
    }

//    public static void compensationSalesReview(Connection connection, Scanner scanner) {
//        while (true) {
//            int compensationChoice = displayCompensationSalesMenu(scanner);
//
//            if (compensationChoice == 1) {
//                // 보상 판매 심사 상세 조회 모듈 호출
//                compensationSalesDetail(connection, scanner);
//            } else if (compensationChoice == 2) {
//                // 보상 판매 심사 상세 조회 모듈 호출
//                compensationSalesDetail(connection, scanner);
//            } else if (compensationChoice == 3) {
//                // 보상 판매 심사 상세 조회 모듈 호출
//                compensationSalesDetail(connection, scanner);
//            } else if (compensationChoice == 4) {
//                // 보상 판매 심사 상세 조회 모듈 호출
//                compensationSalesDetail(connection, scanner);
//            } else if (compensationChoice == 5) {
//                break;
//            } else {
//                System.out.println("잘못된 선택입니다.");
//            }
//        }
//    }

    public static void compensationSalesReview(Connection connection, Scanner scanner) {
        while (true) {
            int compensationChoice;
            try {
                compensationChoice = displayCompensationSalesMenu(connection, scanner);
            } catch (SQLException e) {
                System.out.println("보상 판매 심사 조회 중 오류가 발생했습니다.");
                e.printStackTrace();
                break;
            }

            if (compensationChoice == 0) {
                break;
            } else {
                // 선택한 보상 판매 심사 상세 조회 로직 추가
                displayCompensationSalesDetail(connection, scanner, compensationChoice);
            }
        }
    }

    public static int displayCompensationSalesMenu(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("----- 보상 판매 심사 -----");

        String query = "SELECT ti.trade_in_id, u.user_name, u.user_id, p.product_name, ti.app_date " +
                "FROM trade_in ti " +
                "JOIN users u ON ti.id = u.id " +
                "JOIN product p ON ti.product_id = p.product_id";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int tradeInId = resultSet.getInt("trade_in_id");
                String userName = resultSet.getString("user_name");
                String userId = resultSet.getString("user_id");
                String productName = resultSet.getString("product_name");
                Date appDate = resultSet.getDate("app_date");

                System.out.printf("%d. %s - %s (%s) - %s\n", tradeInId, appDate, userName, userId, productName);
            }
        }

        System.out.println("0. 뒤로 가기");
        System.out.print("선택: ");
        return scanner.nextInt();
    }


    public static void compensationSalesDetail(Connection connection, Scanner scanner) {
        // 보상 판매 심사 상세 조회 모듈 구현
        // ...
    }
    public static void displayCompensationSalesDetail(Connection connection, Scanner scanner, int tradeInId) {
        try {
            String query = "SELECT ti.trade_in_id, u.user_name, u.user_id, p.product_name, ti.is_approved, ti.app_date " +
                    "FROM trade_in ti " +
                    "JOIN users u ON ti.id = u.id " +
                    "JOIN product p ON ti.product_id = p.product_id " +
                    "WHERE ti.trade_in_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, tradeInId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String userName = resultSet.getString("user_name");
                    String userId = resultSet.getString("user_id");
                    String productName = resultSet.getString("product_name");
                    String isApproved = resultSet.getString("is_approved");
                    Date appDate = resultSet.getDate("app_date");

                    System.out.println("----- 보상 판매 심사 상세 -----");
                    System.out.println("보상 판매 ID: " + tradeInId);
                    System.out.println("신청자: " + userName + " (" + userId + ")");
                    System.out.println("상품명: " + productName);
                    System.out.println("승인 여부: " + (isApproved.equals("W") ? "대기 중" : (isApproved.equals("Y") ? "승인" : "거절")));
                    System.out.println("신청일: " + appDate);
                } else {
                    System.out.println("해당 보상 판매 심사를 찾을 수 없습니다.");
                }
            }

            System.out.println("0. 뒤로 가기");
            System.out.print("선택: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                return;
            }
        } catch (SQLException e) {
            System.out.println("보상 판매 심사 상세 조회 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }




}