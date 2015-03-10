package cn.com.spinachzzz.spinachuncle;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import cn.com.spinachzzz.spinachuncle.adapter.MainListViewAdapter;
import cn.com.spinachzzz.spinachuncle.dao.ConfigurationDao;
import cn.com.spinachzzz.spinachuncle.handler.TaskServiceMessageHandler;
import cn.com.spinachzzz.spinachuncle.timer.TimerManager;
import cn.com.spinachzzz.spinachuncle.vo.GlobelSettings;
import cn.com.spinachzzz.spinachuncle.vo.TaskSettings;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ListView listView;

    private ConfigurationDao configDao;

    private GlobelSettings globelSetting;

    private MainActivityDialogs dialogs;

    private TimerManager timeManager;

    public ConfigurationDao getConfigDao() {
	return configDao;
    }

    public GlobelSettings getGlobelSetting() {
	return globelSetting;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	listView = (ListView) this.findViewById(R.id.main_list_view);

	configDao = new ConfigurationDao(this);
	globelSetting = configDao.getGlobalSetting();

	List<Map<String, Object>> list = configDao.getMainItemList();

	MainListViewAdapter adapter = new MainListViewAdapter(
		this,
		list,
		R.layout.main_list_item,
		new String[] { TaskSettings.CONFIG_IMG, TaskSettings.LABEL },
		new int[] { R.id.main_list_item_img, R.id.main_list_item_title });

	listView.setAdapter(adapter);

	dialogs = new MainActivityDialogs(this);

	timeManager = new TimerManager(this);
        startTimeTask();
	
	NotificationManager manager = (NotificationManager) this
		.getSystemService(MainActivity.NOTIFICATION_SERVICE);
	manager.cancel(TaskServiceMessageHandler.NOTIFY_ID);

    }

    public void startTimeTask() {
	timeManager.startTasks();
	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle item selection
	switch (item.getItemId()) {
	case R.id.menu_settings:
	    this.showDialog(Constants.MAIN_SETTING_DIALOG_ID);

	    return true;

	case R.id.menu_exit:
	    Intent intent = new Intent(Intent.ACTION_MAIN);
	    intent.addCategory(Intent.CATEGORY_HOME);
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	    return true;

	default:
	    return super.onOptionsItemSelected(item);

	}
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id, Bundle bundle) {
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	LayoutInflater factory = LayoutInflater.from(this);

	if (id == Constants.MAIN_SETTING_DIALOG_ID) {
	    dialogs.createGlobelSettingDialog(builder, factory);

	    return builder.create();
	} else if (id >= Constants.TASK_SETTING_DIALOG_ID && id < 100) {
	    dialogs.createTaskSettingDialog(bundle, builder, factory);

	    return builder.create();
	}
	return super.onCreateDialog(id);
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	configDao.close();
	
    }

}
