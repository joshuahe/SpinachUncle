package cn.com.spinachzzz.spinachuncle.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcUtils {
    public static final BigDecimal NUM_100 = new BigDecimal("100");

    public static int calculatePercent(int downLoadFileSize, BigDecimal total) {
	BigDecimal dSize = new BigDecimal(downLoadFileSize);

	BigDecimal percent = dSize.multiply(NUM_100).divide(total, 0,
		RoundingMode.HALF_UP);

	return percent.intValue();

    }

}
