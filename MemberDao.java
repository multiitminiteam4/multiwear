package multiwear.member.model.dao;


import multiwear.member.model.dto.MemberDto;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static com.multi.jdbc.common.JDBCTemplate.getConnection;


public class MemberDao {
    private Properties prop = null;
    private Connection conn = null;
    public String currentId = null;

    public MemberDao() {
        try {
            prop = new Properties();
            prop.load(new FileReader("resources/query.properties"));
            conn = getConnection();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


        // 회원추가
    public void memberJoin(MemberDto memberDto) {

        ResultSet rs = null;

        // 아이디 중복 체크
        String Sql = prop.getProperty("selectById");
        try {
            PreparedStatement ps= conn.prepareStatement(Sql);
            ps.setString(1, memberDto.getUser_Id());
            rs = ps.executeQuery();

            // 중복
            if(rs.next()) {
                System.out.println("이미 존재하는 아이디입니다.");
            }else { // 중복 아니면
                String insertSql = prop.getProperty("insertMember");
                PreparedStatement createMemberPs = conn.prepareStatement(insertSql);

                createMemberPs.setString(1, memberDto.getUser_Id());
                createMemberPs.setString(2, memberDto.getUserName());
                createMemberPs.setString(3, memberDto.getEmail());
                createMemberPs.setString(4, memberDto.getPassword());
                createMemberPs.setString(5, memberDto.getPhone());

                createMemberPs.executeUpdate();

                System.out.println("회원가입이 완료되었습니다!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 로그인
    public boolean memberSeach(MemberDto memberDto) {
        String Sql = prop.getProperty("selectByIdPw");
        try {
            PreparedStatement LogInPs = conn.prepareStatement(Sql);
            LogInPs.setString(1, memberDto.getUser_Id());
            LogInPs.setString(2, memberDto.getPassword());
            ResultSet rs = LogInPs.executeQuery();

            if (rs.next()) {
                System.out.println("로그인 성공");
                return true;

            } else {
                System.out.println("로그인 실패");
                System.exit(0);
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // 회원 탈퇴
    public void deleteMember(String user_Id) {

        String Sql = prop.getProperty("deleteMember");
        try {
            PreparedStatement deletePs = conn.prepareStatement(Sql);
            deletePs.setString(1, user_Id);
            deletePs.executeUpdate();

            System.out.println("회원 탈퇴 완료");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 마이페이지 내 회원 정보 수정
    public void updateMember(int change, String newPw) {
        String Sql = null;

        switch (change) {
            case 1:
                Sql = prop.getProperty("updatePassword");
                break;
            default:
                System.out.println("잘못입력하셨습니다.");
                break;
        }
        try {
            PreparedStatement updatePs = conn.prepareStatement(Sql);
            updatePs.setString(1, newPw);
            updatePs.setString(2, currentId);
            updatePs.executeUpdate();
            System.out.println("회원 정보 수정");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    }
