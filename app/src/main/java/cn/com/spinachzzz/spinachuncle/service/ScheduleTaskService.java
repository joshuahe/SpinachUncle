package cn.com.spinachzzz.spinachuncle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;

import cn.com.spinachzzz.spinachuncle.util.DateUtils;
import cn.com.spinachzzz.spinachuncle.util.WifiUtils;
import cn.com.spinachzzz.spinachuncle.vo.GlobelSettings;

public class ScheduleTaskService extends Service {
    private static final String TAG = ScheduleTaskService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

	GlobelSettings globelSettings =null;

	if (globelSettings.getAutoConnect()) {

	    if (DateUtils.passScheduleTime(globelSettings.getScheduleTimeCal())
		    && !DateUtils.isToday(globelSettings.getLastFinishTime())) {
		
		if (WifiUtils.checkOnlineState(this,
			globelSettings.getOnlyWifi())) {

            /**
		    List<TaskSettings> list = configDao.getTaskSettingList();

		    TaskPool taskPool = TaskPool.getInstance();

		    for (TaskSettings task : list) {
			if (task.getScheduleAble().booleanValue() == true) {
			    TaskServiceMessageHandler handler = new TaskServiceMessageHandler(
				    this);

			    BaseDownloader downloader = DownloaderFactory
				    .createDownloader(task);
			    downloader.setHandler(handler);
			    downloader.setConfigurationDao(configDao);
			    downloader.setTaskSetting(task);

			    Log.i(TAG, "submit");
			    taskPool.submit(downloader);
			}
		    }
**/
		    globelSettings.setLastFinishTime(new Date());
		   // configDao.updateGlobelSetting(globelSettings);
		}
	    }

	}
	return START_NOT_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
	Log.i(TAG, "ScheduleTaskService-->onBind");
	return null;
    }

    @Override
    public void onCreate() {
	Log.i(TAG, "ScheduleTaskService-->onCreate");
	//configDao = new ConfigurationDao(this);

	super.onCreate();
    }

    @Override
    public void onDestroy() {
	Log.i(TAG, "ScheduleTaskService-->onDestroy");
	//configDao.close();
	super.onDestroy();
    }

}
