package cn.com.spinachzzz.spinachuncle.receiver;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.dao.BaseDownloader;
import cn.com.spinachzzz.spinachuncle.dao.ConfigurationDao;
import cn.com.spinachzzz.spinachuncle.dao.DownloaderFactory;
import cn.com.spinachzzz.spinachuncle.handler.TaskServiceMessageHandler;
import cn.com.spinachzzz.spinachuncle.service.TaskPool;
import cn.com.spinachzzz.spinachuncle.util.DateUtils;
import cn.com.spinachzzz.spinachuncle.util.WifiUtils;
import cn.com.spinachzzz.spinachuncle.vo.GlobelSettings;
import cn.com.spinachzzz.spinachuncle.vo.TaskSettings;

/**
 * @deprecated Not used. Only for code reference.
 * @author Admin
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    private static final String TAG = ConnectionChangeReceiver.class
	    .getSimpleName();

    private ConfigurationDao configDao;

    @Override
    public void onReceive(Context context, Intent intent) {
	Log.i(TAG, "OnReceive connectionChange");

	configDao = new ConfigurationDao(context);
	GlobelSettings globelSettings = configDao.getGlobalSetting();

	if (globelSettings.getAutoConnect()) {

	    if (WifiUtils.checkOnlineState(context,
		    globelSettings.getOnlyWifi())) {

		if (DateUtils.passInterval(globelSettings.getLastFinishTime(),
			Constants.TASK_INTERVAL_HOUR)) {

		    List<TaskSettings> list = configDao.getTaskSettingList();

		    TaskPool taskPool = TaskPool.getInstance();
		    for (TaskSettings task : list) {
			if (task.getScheduleAble().booleanValue() == true) {
			    TaskServiceMessageHandler handler = new TaskServiceMessageHandler(
				    context);

			    BaseDownloader downloader = DownloaderFactory
				    .createDownloader(task);
			    downloader.setHandler(handler);
			    downloader.setConfigurationDao(configDao);
			    downloader.setTaskSetting(task);

			    Log.i(TAG, "submit");
			    taskPool.submit(downloader);
			}

		    }

		}

	    }
	}

    }
}
