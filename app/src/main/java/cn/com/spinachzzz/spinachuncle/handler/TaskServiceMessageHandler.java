package cn.com.spinachzzz.spinachuncle.handler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.com.spinachzzz.spinachuncle.MainActivity;
import cn.com.spinachzzz.spinachuncle.R;

public class TaskServiceMessageHandler extends Handler {
    private static final String TAG = TaskServiceMessageHandler.class.getSimpleName();

    private Context context;

    private Notification notification;
    private NotificationManager manager;
    private PendingIntent contentIntent;

    public static final int NOTIFY_ID = 0;

    public final static int DOWNLOAD_COMPLETE = 0;
    public final static int DOWNLOAD_FAIL = 1;
    public final static int DOWNLOADING = 2;

    public final static String MSG_KEY = "MSG";

    public TaskServiceMessageHandler(Context context) {
	super();
	this.context = context;
    }
    
    public String getString(int resId){
	return context.getString(resId);
    }

    public void updateMessage(String msgTitle, String msgContent) {
	notification.tickerText = msgTitle;
	notification.setLatestEventInfo(context, msgTitle, msgContent,
		contentIntent);

	manager.notify(NOTIFY_ID, notification);
    }

    public void startNotify() {
	manager = (NotificationManager) context
		.getSystemService(Service.NOTIFICATION_SERVICE);
	Intent intent = new Intent(context, MainActivity.class);
	contentIntent = PendingIntent.getActivity(context, 0, intent,
		PendingIntent.FLAG_UPDATE_CURRENT);

	notification = new Notification();
	notification.icon = R.drawable.logo_down;
	notification.tickerText = context.getText(R.string.start_download);
	notification.when = System.currentTimeMillis();

	notification.setLatestEventInfo(context,
		context.getString(R.string.download_start),
		context.getString(R.string.download_start), contentIntent);

	manager.notify(NOTIFY_ID, notification);
    }

    @Override
    public void handleMessage(Message msg) {
	notification = new Notification();
	notification.icon = R.drawable.logo_down;
	notification.when = System.currentTimeMillis();

	switch (msg.what) {
	case DOWNLOAD_COMPLETE:
	    notification.tickerText = context.getText(R.string.download_succ);
	    notification.setLatestEventInfo(context, context
		    .getString(R.string.download_succ), msg.getData()
		    .getString(MSG_KEY), contentIntent);

	    break;
	case DOWNLOAD_FAIL:
	    notification.tickerText = context.getText(R.string.download_fail);
	    notification.setLatestEventInfo(context, context
		    .getString(R.string.download_fail), msg.getData()
		    .getString(MSG_KEY), contentIntent);
	    break;

	case DOWNLOADING:
	    notification.tickerText = context.getText(R.string.downloading);
	    notification.setLatestEventInfo(context, context
		    .getString(R.string.downloading),
		    msg.getData().getString(MSG_KEY), contentIntent);
	    break;
	}

//	if (msg.what != DOWNLOADING) {
//	    notification.defaults = Notification.DEFAULT_ALL;
//	}

	manager.notify(NOTIFY_ID, notification);
    }

}
