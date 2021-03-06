package cn.com.spinachzzz.spinachuncle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;
import java.util.Map;

import cn.com.spinachzzz.spinachuncle.adapter.MainListViewAdapter;
import cn.com.spinachzzz.spinachuncle.business.ApplicationContext;
import cn.com.spinachzzz.spinachuncle.dao.DatabaseHelper;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.handler.TaskServiceMessageHandler;
import cn.com.spinachzzz.spinachuncle.receiver.MainMessageReceiver;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ListView listView;

    private Button addBtn;

    private TextView messageTextView;

    private MainActivityDialogs dialogs;

    private RuntimeExceptionDao<Tasks, String> taskRuntimeDao;

    private MainMessageReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) this.findViewById(R.id.main_list_view);

        addBtn = (Button) this.findViewById(R.id.main_btn_add);

        messageTextView = (TextView) this.findViewById(R.id.main_message);

        taskRuntimeDao = this.getHelper().getTasksDao();

        initBtns();

        List<Tasks> tasks = taskRuntimeDao.queryForAll();

        List<? extends Map<String, ?>> tasksData = ApplicationContext.getInstance().getTasksService().convertToAdapterMap(tasks);

        MainListViewAdapter adapter = new MainListViewAdapter(
                this,
                tasksData,
                R.layout.main_list_item,
                new String[]{"label"},
                new int[]{R.id.main_list_item_title});

        listView.setAdapter(adapter);

        dialogs = new MainActivityDialogs(this);

        initReceiver();

        TaskServiceMessageHandler.cancelNotification(this);

    }

    public MainActivityDialogs getDialogs() {
        return dialogs;
    }

    private void initBtns() {

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.showDialog(Constants.TASK_ADD_DIALOG_ID);

            }
        });
    }

    private void initReceiver() {
        receiver = new MainMessageReceiver();
        receiver.setMainActivity(this);
        IntentFilter intentFilter = new IntentFilter(Constants.MAIN_MESSAGE_BROADCAST);
        registerReceiver(receiver, intentFilter);
    }

    public void updateMessages(String message) {
        messageTextView.setText(message);
    }


    public RuntimeExceptionDao<Tasks, String> getTaskRuntimeDao() {
        return taskRuntimeDao;
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
    protected Dialog onCreateDialog(int id, Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);

        if (id == Constants.TASK_ADD_DIALOG_ID) {
            return dialogs.createAddTaskDialog(builder, factory);
        } else if (id == Constants.MAIN_SETTING_DIALOG_ID) {
            //dialogs.createGlobelSettingDialog(builder, factory);

            return builder.create();
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {

        this.unregisterReceiver(receiver);
        super.onDestroy();
    }

}
