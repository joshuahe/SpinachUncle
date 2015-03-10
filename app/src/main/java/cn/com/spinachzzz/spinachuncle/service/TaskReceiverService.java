package cn.com.spinachzzz.spinachuncle.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;
import cn.com.spinachzzz.spinachuncle.receiver.ConnectionChangeReceiver;

/**
 * @deprecated Not used. Only for code reference.
 * @author Admin
 */
public class TaskReceiverService extends Service {

    private static final String TAG = TaskReceiverService.class.getSimpleName();

    private ConnectionChangeReceiver connectionChangeReceiver;

    @Override
    public IBinder onBind(Intent intent) {
	Log.i(TAG, "TaskReceiverService-->onBind");
	return null;
    }

    @Override
    public void onCreate() {
	Log.i(TAG, "TaskReceiverService-->onCreate");
	super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	Log.i(TAG, "TaskReceiverService-->onStartCommand");

	registerConnectionChangeReceiver();

	return START_NOT_STICKY;
    }

    private void registerConnectionChangeReceiver() {
	connectionChangeReceiver = new ConnectionChangeReceiver();

	IntentFilter filter = new IntentFilter();
	filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
	registerReceiver(connectionChangeReceiver, filter);
    }

    @Override
    public void onDestroy() {
	Log.i(TAG, "TaskReceiverService-->onDestroy");
	super.onDestroy();

	unregisterReceiver(connectionChangeReceiver);
    }

}
