package cn.com.spinachzzz.spinachuncle.util;

public class StringUtils {

    public static String getFileName(String str) {
	if (str == null) {
	    return "";
	}

	int posE = str.lastIndexOf('.');
	int posS = str.lastIndexOf('/');
	if (posE != -1 && posS != -1 && posE > posS+1) {
	    return str.substring(posS+1, posE);
	} else {
	    return str;
	}
    }
}
