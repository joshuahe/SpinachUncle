package cn.com.spinachzzz.spinachuncle.util;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class StringUtilsTest {


    @Test
    public void testReplaceWithDateFormat() {
        String str = "http://mod.cri.cn/eng/ez/morning/[yyyy]/ezm[yyMMdd].mp3";
       System.out.println(StringUtils.replaceWithDateFormat(new Date(), str));
    }
}