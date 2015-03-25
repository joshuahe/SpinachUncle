package cn.com.spinachzzz.spinachuncle.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.R;
import cn.com.spinachzzz.spinachuncle.dao.BaseDownloader;
import cn.com.spinachzzz.spinachuncle.dao.DownloaderFactory;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.handler.TaskServiceMessageHandler;
import cn.com.spinachzzz.spinachuncle.util.WifiUtils;
import cn.com.spinachzzz.spinachuncle.vo.GlobelSettings;
import cn.com.spinachzzz.spinachuncle.vo.TaskExtraVO;

public class SingleTaskService extends Service {

    //private ConfigurationDao configDao;

    private static final String TAG = SingleTaskService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "SingleTaskService-->onStartCommand");

        Tasks task = (Tasks)intent.getSerializableExtra("task");
        TaskExtraVO extra = (TaskExtraVO)intent.getSerializableExtra("extra");

        TaskServiceMessageHandler handler = new TaskServiceMessageHandler(this);

        if (WifiUtils.checkOnlineState(this, true)) {
            TaskPool taskPool = TaskPool.getInstance();

            BaseDownloader downloader = DownloaderFactory
                    .createDownloader(task);
            downloader.setTaskExtraVO(extra);
            downloader.setHandler(handler);

            taskPool.submit(downloader);


        } else {
            handler.updateMessage(getString(R.string.no_internet_conn),
                    getString(R.string.download_fail));
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "TaskService-->onBind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "TaskService-->onCreate");
        //configDao = new ConfigurationDao(this);

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "TaskService-->onDestroy");
        //configDao.close();
        super.onDestroy();
    }

}
