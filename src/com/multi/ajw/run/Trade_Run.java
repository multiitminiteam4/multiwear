package com.multi.ajw.run;

import com.multi.ajw.trade.view.Trade_add;

import java.util.Scanner;

public class Trade_Run {
    public static String auth;
    public static void main(String[] args) {
        auth = new Trade_add().auth();
        if(auth.equals("root")) {//임시 root와 user 선택
            new Trade_add().trade_root();
        }else {
            new Trade_add().trade_user();
        }
    }
}
//대략적인 기능 구현 완료
//더 해야할 일
//가격에 따른 point 지급, 데이터베이스 정리,그에 맞춰 쿼리문 수정