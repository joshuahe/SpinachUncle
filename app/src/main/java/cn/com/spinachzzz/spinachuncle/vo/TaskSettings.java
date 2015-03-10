package cn.com.spinachzzz.spinachuncle.vo;

import android.database.Cursor;

public class TaskSettings {
    public static final String CONFIG_IMG = "IMG";
    public static final String ID = "ID";
    public static final String CODE = "CODE";
    public static final String LABEL = "LABEL";
    
    public static final String SCHEDULE_ABLE = "SCHEDULE_ABLE";
    public static final String MAX_KEEP = "MAX_KEEP";

    private Long id;
    private String code;
    private String label;
   
    private Boolean scheduleAble;
    private Integer maxKeep;

    public TaskSettings() {

    }

    public TaskSettings(Cursor cursor) {
	this.id = cursor.getLong(cursor.getColumnIndex(ID));
	this.code = cursor.getString(cursor.getColumnIndex(CODE));
	this.label = cursor.getString(cursor.getColumnIndex(LABEL));
	
	this.scheduleAble = cursor.getInt(cursor.getColumnIndex(SCHEDULE_ABLE)) == 1 ? true
		: false;
	this.maxKeep = cursor.getInt(cursor.getColumnIndex(MAX_KEEP));
    }

    public static String[] getColumns() {
	String[] res = { ID, CODE, LABEL, SCHEDULE_ABLE,
		MAX_KEEP };
	return res;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public Boolean getScheduleAble() {
	return scheduleAble;
    }

    public void setScheduleAble(Boolean scheduleAble) {
	this.scheduleAble = scheduleAble;
    }

    public Integer getMaxKeep() {
	return maxKeep;
    }

    public int getMaxKeepPos() {
	switch (maxKeep.intValue()) {
	case 10:
	    return 0;
	case 20:
	    return 1;
	case 50:
	    return 2;
	case 100:
	    return 3;
	default:
	    return 4;
	}
    }

    public void setMaxKeepByPos(int pos) {
	switch (pos) {
	case 0:
	    maxKeep = 10;
	    break;
	case 1:
	    maxKeep = 20;
	    break;
	case 2:
	    maxKeep = 50;
	    break;
	case 3:
	    maxKeep = 100;
	    break;
	default:
	    maxKeep = -1;
	    break;
	}
    }

    public void setMaxKeep(Integer maxKeep) {
	this.maxKeep = maxKeep;
    }

    @Override
    public String toString() {
	return "TaskSettings [id=" + id + ", code=" + code + ", label=" + label
		+ ", scheduleAble="
		+ scheduleAble + ", maxKeep=" + maxKeep + "]";
    }

    

}
