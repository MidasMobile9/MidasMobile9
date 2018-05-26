package com.test.midasmobile9.util;

import java.util.ArrayList;
import java.util.Arrays;

public class ParseServerState {
    private static ArrayList<String> stateStr = new ArrayList<String>(
            Arrays.asList(
                    "주문 확인 전",
                    "주문 확인",
                    "제조 중",
                    "제조 완료",
                    "테이크아웃 완료"
            )
    );

    public static String getState(int state){
        return stateStr.get(state);
    }
}
