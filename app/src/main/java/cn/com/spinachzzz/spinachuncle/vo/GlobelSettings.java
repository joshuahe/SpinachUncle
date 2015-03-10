package cn.com.spinachzzz.spinachuncle.vo;

import java.util.Calendar;
import java.util.Date;

import android.database.Cursor;
import cn.com.spinachzzz.spinachuncle.util.DateUtils;

public class GlobelSettings {
    public static final String SCHEDULE_TIME = "SCHEDULE_TIME";
    public static final String LAST_FINISH_TIME = "LAST_FINISH_TIME";
    public static final String ONLY_WIFI = "ONLY_WIFI";
    public static final String AUTO_CONNECT = "AUTO_CONNECT";

    private Calendar scheduleTimeCal;
    private Boolean onlyWifi;
    private Boolean autoConnect;
    private Date lastFinishTime;

    public GlobelSettings() {

    }

    public GlobelSettings(Cursor cursor) {
	Date scheduleTime = DateUtils.parseSimpleDayTime(cursor
		.getString(cursor.getColumnIndex(SCHEDULE_TIME)));
	this.scheduleTimeCal = Calendar.getInstance();
	this.scheduleTimeCal.setTime(scheduleTime);
	this.lastFinishTime = DateUtils.parseSimpleDayTime(cursor
		.getString(cursor.getColumnIndex(LAST_FINISH_TIME)));
	this.onlyWifi = cursor.getInt(cursor.getColumnIndex(ONLY_WIFI)) == 1 ? true
		: false;
	this.autoConnect = cursor.getInt(cursor.getColumnIndex(AUTO_CONNECT)) == 1 ? true
		: false;
    }

    public static String[] getColumns() {
	String[] res = { SCHEDULE_TIME, LAST_FINISH_TIME, ONLY_WIFI, AUTO_CONNECT };
	return res;
    }

    public String getScheduleTimeStr() {
	return DateUtils.formatSimpleDayTime(scheduleTimeCal.getTime());
    }

    public Integer getScheduleHour() {
	return this.scheduleTimeCal.get(Calendar.HOUR_OF_DAY);
    }

    public Integer getScheduleMin() {
	return this.scheduleTimeCal.get(Calendar.MINUTE);
    }

    public void setScheduleTime(Integer hour, Integer min) {
	scheduleTimeCal.set(Calendar.HOUR_OF_DAY, hour);
	scheduleTimeCal.set(Calendar.MINUTE, min);
    }

    public Calendar getScheduleTimeCal() {
	return scheduleTimeCal;
    }
    
    public Date getLastFinishTime() {
   	return lastFinishTime;
       }

       public String getLastFinishTimeStr() {
   	return DateUtils.formatSimpleDayTime(lastFinishTime);
       }

       public void setLastFinishTime(Date lastFinishTime) {
   	this.lastFinishTime = lastFinishTime;
       }

    public Boolean getOnlyWifi() {
	return onlyWifi;
    }

    public Integer getOnlyWifiInt() {
	return onlyWifi ? 1 : 0;
    }

    public void setOnlyWifi(Boolean onlyWifi) {
	this.onlyWifi = onlyWifi;
    }

    public Boolean getAutoConnect() {
	return autoConnect;
    }

    public Integer getAutoConnectInt() {
	return autoConnect ? 1 : 0;
    }

    public void setAutoConnect(Boolean autoConnect) {
	this.autoConnect = autoConnect;
    }

    @Override
    public String toString() {
	return "GlobelSettings [scheduleTimeCal=" + scheduleTimeCal
		+ ", onlyWifi=" + onlyWifi + ", autoConnect=" + autoConnect
		+ "]";
    }

}
