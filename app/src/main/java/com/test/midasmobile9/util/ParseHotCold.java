package com.test.midasmobile9.util;

public class ParseHotCold {
    private static final String HOT = "HOT";
    private static final String COLD = "COLD";
    private static final String HOT_COLD = "HOT and COLD";

    public static String getHotCold(int index) {
        if ( index == 0 ) {
            return HOT;
        } else if ( index == 1 ) {
            return COLD;
        } else if ( index == 2 ) {
            return HOT_COLD;
        }

        return null;
    }

    public static int getHotColdInt(String hotcold){
        switch (hotcold){
            case HOT:
                return 0;
            case COLD:
                return 1;
        }
        return 0;
    }
}
