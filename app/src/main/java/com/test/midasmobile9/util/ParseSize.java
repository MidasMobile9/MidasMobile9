package com.test.midasmobile9.util;

public class ParseSize {
    private static final String SMALL = "S";
    private static final String Medium = "M";
    private static final String Large = "L";

    public static String getSize(int index) {
        if ( index == 0 ) {
            return SMALL;
        } else if ( index == 1 ) {
            return Medium;
        } else if ( index == 2 ) {
            return Large;
        }

        return null;
    }

    public static int getSizeInt(String size){
        switch (size){
            case SMALL:
                return 0;
            case Medium:
                return 1;
            case Large:
                return 2;
        }
        return 0;
    }
}
