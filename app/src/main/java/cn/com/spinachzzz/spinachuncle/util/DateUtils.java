package cn.com.spinachzzz.spinachuncle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final SimpleDateFormat simpleDayFormat = new SimpleDateFormat(
	    "yyyy-MM-dd", Locale.US);
    public static final SimpleDateFormat simpleDayTimeFormat = new SimpleDateFormat(
	    "yyyy-MM-dd HH:mm:sss", Locale.US);

    public static final SimpleDateFormat easyFmTimeFormat = new SimpleDateFormat(
	    "yyMMdd", Locale.US);

    public static String formatSimpleDay(Date date) {
	return simpleDayFormat.format(date);
    }

    public static Date parseSimpleDayTime(String string) {
	try {
	    Date date = simpleDayTimeFormat.parse(string);
	    return date;
	} catch (ParseException e) {
	    return null;
	}
    }

    public static String formatSimpleDayTime(Date date) {
	return simpleDayTimeFormat.format(date);
    }

    public static boolean passInterval(Date date, int intervalHour) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);

	cal.add(Calendar.HOUR_OF_DAY, intervalHour);

	if (cal.compareTo(Calendar.getInstance()) > 0) {
	    return false;
	} else {
	    return true;
	}

    }

    public static boolean passScheduleTime(Calendar scheduleTimeCal) {

	Calendar todaySchedule = Calendar.getInstance();
	todaySchedule.set(Calendar.HOUR_OF_DAY,
		scheduleTimeCal.get(Calendar.HOUR_OF_DAY));
	todaySchedule
		.set(Calendar.MINUTE, scheduleTimeCal.get(Calendar.MINUTE));
	todaySchedule.set(Calendar.MILLISECOND,
		scheduleTimeCal.get(Calendar.MILLISECOND));

	if (todaySchedule.compareTo(Calendar.getInstance()) > 0) {
	    return false;
	} else {
	    return true;
	}
    }

    public static String formatEasyFmDate(Date date) {
	return easyFmTimeFormat.format(date);
    }

    public static boolean isToday(Date date) {
	Calendar today = Calendar.getInstance();

	Calendar theDay = Calendar.getInstance();
	theDay.setTime(date);

	if (today.get(Calendar.YEAR) == theDay.get(Calendar.YEAR)
		&& today.get(Calendar.DAY_OF_YEAR) == theDay
			.get(Calendar.DAY_OF_YEAR)) {
	    return true;
	}
	return false;
    }
}
