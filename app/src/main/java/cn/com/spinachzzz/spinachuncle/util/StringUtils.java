package cn.com.spinachzzz.spinachuncle.util;

import java.util.UUID;

public class StringUtils {

    public static String createUUID() {
        return UUID.randomUUID().toString();
    }


    public static boolean isURL(String url) {
        if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
            return true;
        }

        return false;
    }


    public static String removeBraces(String input) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '{' && c != '}') {
                sb.append(c);
            }
        }

        return sb.toString();
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
