package cn.com.spinachzzz.spinachuncle.timer;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.service.ScheduleTaskService;

public class TimerManager {
    private Context context;

    public TimerManager(Context context) {
	this.context = context;
    }

    public Date startTasks() {
	AlarmManager alarmManager = (AlarmManager) context
		.getSystemService(Context.ALARM_SERVICE);

	Date nextTime = getNextTime();

	alarmManager.setRepeating(AlarmManager.RTC, nextTime.getTime(),
		getInterval(), getOperation());

	return nextTime;

    }

    private Date getNextTime() {
	Calendar calendar = Calendar.getInstance();

	calendar.add(Calendar.MINUTE, Constants.TASK_CHECK_MIN);

	Date date = calendar.getTime();
	return date;
    }

    private long getInterval() {

	return Constants.TASK_CHECK_MIN * 60 * 1000;
    }

    private PendingIntent getOperation() {
	Intent intent = new Intent(context, ScheduleTaskService.class);

	PendingIntent pendingIntent = PendingIntent.getService(context,
		Constants.TIMER_SERVICE_CODE, intent,
		PendingIntent.FLAG_UPDATE_CURRENT);
	return pendingIntent;
    }

}
