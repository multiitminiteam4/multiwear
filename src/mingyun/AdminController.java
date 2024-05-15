package mingyun;

import java.sql.*;

public class AdminController {
    private Connection connection;
    private String currentUserId;

    public AdminController(Connection connection, String currentUserId) {
        this.connection = connection;
        this.currentUserId = currentUserId;
    }

    // 주문내역 확인 기능
    public void viewOrderHistory() {
        if (isAdmin()) {
            String query = "SELECT * FROM orders";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                System.out.println("주문내역:");
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    int userId = resultSet.getInt("id");
                    int payAmount = resultSet.getInt("pay_amount");
                    String address = resultSet.getString("address");
                    Date orderedAt = resultSet.getDate("ordered_at");
                    System.out.printf("주문번호: %d, 사용자ID: %d, 결제금액: %d, 주소: %s, 주문일시: %s%n",
                            orderId, userId, payAmount, address, orderedAt);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("관리자 권한이 없습니다.");
        }
    }

    // 유저 조회 기능
    public void viewUsers() {
        if (isAdmin()) {
            String query = "SELECT * FROM users";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                System.out.println("유저 목록:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String userId = resultSet.getString("user_id");
                    String userName = resultSet.getString("user_name");
                    String email = resultSet.getString("email");
                    String phone = resultSet.getString("phone");
                    int point = resultSet.getInt("point");
                    Date createdAt = resultSet.getDate("created_at");
                    System.out.printf("ID: %d, 사용자ID: %s, 이름: %s, 이메일: %s, 전화번호: %s, 포인트: %d, 가입일: %s%n",
                            id, userId, userName, email, phone, point, createdAt);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("관리자 권한이 없습니다.");
        }
    }

    // 회원 탈퇴 기능
    public void deleteUser(String userId) {
        if (isAdmin()) {
            String query = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("회원 탈퇴 처리되었습니다.");
                } else {
                    System.out.println("해당 사용자를 찾을 수 없습니다.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("관리자 권한이 없습니다.");
        }
    }

    // 현재 사용자의 관리자 권한 확인
    private boolean isAdmin() {
        String query = "SELECT r.role_name FROM user_role ur " +
                "JOIN roles r ON ur.role_id = r.role_id " +
                "WHERE ur.id = (SELECT id FROM users WHERE user_id = ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, currentUserId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String roleName = resultSet.getString("role_name");
                return roleName.equals("관리자");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}