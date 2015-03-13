package cn.com.spinachzzz.spinachuncle.util;

import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            if (c != '[' && c != ']') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static String replaceWithDateFormat(Date date, String input) {

        Pattern pattern = Pattern.compile("(\\[)(.*?)(\\])");

        Matcher match = pattern.matcher(input);

        String res = input;

        HashMap<String, String> repMap = new HashMap<String, String>();

        while (match.find()) {
            System.out.println("found match:" + match.group(0));

            String matchStr = match.group(0);
            String replaceStr = DateUtils.formatDate(date, matchStr);
            replaceStr = removeBraces(replaceStr);

            repMap.put(matchStr, replaceStr);

            //System.out.println("replace"+match.replaceFirst(replaceStr));
        }

        for(String key:repMap.keySet()){

            System.out.println("key:"+key);
            System.out.println("value:"+repMap.get(key));

            input = input.replace(key, repMap.get(key));
        }

        return input;
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
