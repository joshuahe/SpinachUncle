package cn.com.spinachzzz.spinachuncle.dao;

import cn.com.spinachzzz.spinachuncle.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
    static final String TAG = "DBOpenHelper";

    private static final int VERSION = 2;
    private static final String DBNAME = "data.db";
    public static final String GLOBEL_SETTINGS = "GLOBEL_SETTINGS";
    public static final String TASK_SETTINGS = "TASK_SETTINGS";

    public DBOpenHelper(Context context) {
	super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL("CREATE TABLE "
		+ GLOBEL_SETTINGS
		+ " (ID INTEGER PRIMARY KEY, SCHEDULE_TIME VARCHAR(20), LAST_FINISH_TIME VARCHAR(20), ONLY_WIFI INTEGER, AUTO_CONNECT INTEGER)");
	db.execSQL("INSERT INTO " + GLOBEL_SETTINGS
		+ " VALUES(null, '2000-01-01 22:00:000', '2000-01-01 22:00:000', 0, 0)");

	db.execSQL("CREATE TABLE "
		+ TASK_SETTINGS
		+ " (ID INTEGER PRIMARY KEY, CODE VARCHAR(50), LABEL VARCHAR(200), SCHEDULE_ABLE INTEGER, MAX_KEEP INTEGER)");
	db.execSQL("INSERT INTO " + TASK_SETTINGS + " VALUES(null, '"
		+ Constants.FLICKR_CODE
		+ "', 'Flickr', 1, 20)");
	db.execSQL("INSERT INTO " + TASK_SETTINGS + " VALUES(null, '"
		+ Constants.MOKO_CODE
		+ "', 'Moko', 0, 20)");
	db.execSQL("INSERT INTO " + TASK_SETTINGS + " VALUES(null, '"
		+ Constants.PCONLINE_CODE
		+ "', 'PC Online', 0, 20)");
	db.execSQL("INSERT INTO " + TASK_SETTINGS + " VALUES(null, '"
		+ Constants.FEIYU_CODE
		+ "', 'Fei Yu Show', 0, 20)");

	Log.i(TAG, "onCreate!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	Log.i(TAG, "onUpgrade!");
    }

}
