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

