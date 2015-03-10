package cn.com.spinachzzz.spinachuncle.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.R;
import cn.com.spinachzzz.spinachuncle.vo.GlobelSettings;
import cn.com.spinachzzz.spinachuncle.vo.TaskSettings;

public class ConfigurationDao {
    private static final String TAG = "ConfigurationDao";
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public ConfigurationDao(Context context) {
	helper = new DBOpenHelper(context);
    }

    public List<TaskSettings> getTaskSettingList() {
	List<TaskSettings> res = new ArrayList<TaskSettings>();

	db = helper.getReadableDatabase();
	String sql = "SELECT * FROM " + DBOpenHelper.TASK_SETTINGS;
	Cursor cursor = db.rawQuery(sql, null);
	while (cursor.moveToNext()) {
	    res.add(new TaskSettings(cursor));
	}
	return res;
    }

    public TaskSettings getTaskSetting(String code) {

	db = helper.getReadableDatabase();

	String selection = TaskSettings.CODE + " = ?";
	String[] param = { code };

	Cursor cursor = db.query(DBOpenHelper.TASK_SETTINGS,
		TaskSettings.getColumns(), selection, param, null, null, null);
	// Cursor cursor = db(sql, null);
	while (cursor.moveToNext()) {
	    return new TaskSettings(cursor);
	}
	return null;
    }

    public List<Map<String, Object>> getMainItemList() {
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	for (TaskSettings t : getTaskSettingList()) {
	    Log.i(TAG, t.toString());

	    Map<String, Object> item = new HashMap<String, Object>();
	    item.put(TaskSettings.ID, t.getId());
	    item.put(TaskSettings.CODE, t.getCode());
	    item.put(TaskSettings.LABEL, t.getLabel());
	    item.put(TaskSettings.CONFIG_IMG, getImgByCode(t.getCode()));
	    list.add(item);
	}

	// item = new HashMap<String, Object>();
	// item.put(Constants.CONFIG_TITLE, "Radio");
	// // item.put(Constants.CONFIG_ID, Integer.valueOf(1));
	// item.put(Constants.CONFIG_IMG, R.drawable.cri);
	// list.add(item);

	return list;
    }

    private int getImgByCode(String code) {
	if (code.equalsIgnoreCase(Constants.FLICKR_CODE)) {
	    return R.drawable.flickr;
	} else if (code.equalsIgnoreCase(Constants.MOKO_CODE)) {
	    return R.drawable.moko;
	} else if (code.equalsIgnoreCase(Constants.PCONLINE_CODE)) {
	    return R.drawable.pclogo;
	} else if (code.equalsIgnoreCase(Constants.TIANYASTAR_CODE)) {
	    return R.drawable.tianyastar;
	} else if (code.equalsIgnoreCase(Constants.FEIYU_CODE)) {
	    return R.drawable.ezlogo;
	}
	return -1;
    }

    public void updateTaskSetting(TaskSettings taskSetting) {
	StringBuilder sql = new StringBuilder();
	sql.append("UPDATE ");
	sql.append(DBOpenHelper.TASK_SETTINGS);
	sql.append(" SET ");
	sql.append(TaskSettings.SCHEDULE_ABLE);
	sql.append(" = ?, ");
	sql.append(TaskSettings.MAX_KEEP);
	sql.append(" = ? WHERE ");
	sql.append(TaskSettings.CODE);
	sql.append(" = ? ");

	Object[] param = { taskSetting.getScheduleAble(),
		taskSetting.getMaxKeep(), taskSetting.getCode() };

	db.execSQL(sql.toString(), param);
    }

    public GlobelSettings getGlobalSetting() {
	db = helper.getReadableDatabase();

	Cursor cursor = db.query(DBOpenHelper.GLOBEL_SETTINGS,
		GlobelSettings.getColumns(), null, null, null, null, null);
	while (cursor.moveToNext()) {
	    return new GlobelSettings(cursor);
	}
	return null;
    }

    public void updateGlobelSetting(GlobelSettings globelSetting) {
	StringBuilder sql = new StringBuilder();
	sql.append("UPDATE ");
	sql.append(DBOpenHelper.GLOBEL_SETTINGS);
	sql.append(" SET ");
	sql.append(GlobelSettings.SCHEDULE_TIME);
	sql.append(" = ?, ");
	sql.append(GlobelSettings.LAST_FINISH_TIME);
	sql.append(" = ?, ");
	sql.append(GlobelSettings.AUTO_CONNECT);
	sql.append(" = ?, ");
	sql.append(GlobelSettings.ONLY_WIFI);
	sql.append(" = ?");

	Log.i(TAG, "to save:" + globelSetting);

	Object[] param = { globelSetting.getScheduleTimeStr(),
		globelSetting.getLastFinishTimeStr(),

		globelSetting.getAutoConnectInt(),
		globelSetting.getOnlyWifiInt() };

	db.execSQL(sql.toString(), param);
    }

    public void close() {
	if (db != null && db.isOpen()) {
	    db.close();
	}

    }
}
