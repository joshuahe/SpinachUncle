package cn.com.spinachzzz.spinachuncle.util;

import java.util.UUID;

public class StringUtils {

    public static String createUUID(){
        return UUID.randomUUID().toString();
    }

    public static String getFileName(String str) {
        if (str == null) {
            return "";
        }

        int posE = str.lastIndexOf('.');
        int posS = str.lastIndexOf('/');
        if (posE != -1 && posS != -1 && posE > posS + 1) {
            return str.substring(posS + 1, posE);
        } else {
            return str;
        }
    }
}
