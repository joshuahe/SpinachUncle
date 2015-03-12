package cn.com.spinachzzz.spinachuncle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.List;
import java.util.Map;

import cn.com.spinachzzz.spinachuncle.adapter.MainListViewAdapter;
import cn.com.spinachzzz.spinachuncle.business.ApplicationContext;
import cn.com.spinachzzz.spinachuncle.dao.DatabaseHelper;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.handler.TaskServiceMessageHandler;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ListView listView;

    private Button addBtn;

    private MainActivityDialogs dialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) this.findViewById(R.id.main_list_view);

        addBtn = (Button) this.findViewById(R.id.main_btn_add);

        List<Tasks> tasks = this.getHelper().getTasksDao().queryForAll();

        List<? extends Map<String, ?>> tasksData = ApplicationContext.getInstance().getTasksService().convertToAdapterMap(tasks);

        MainListViewAdapter adapter = new MainListViewAdapter(
                this,
                tasksData,
                R.layout.main_list_item,
                new String[]{"label"},
                new int[]{R.id.main_list_item_title});

        listView.setAdapter(adapter);

        dialogs = new MainActivityDialogs(this);

        initBtns();

        NotificationManager manager = (NotificationManager) this
                .getSystemService(MainActivity.NOTIFICATION_SERVICE);
        manager.cancel(TaskServiceMessageHandler.NOTIFY_ID);

    }


    private void initBtns(){

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.showDialog(Constants.TASK_ADD_DIALOG_ID);

            }
        });
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

        if (id == Constants.TASK_ADD_DIALOG_ID){
            return dialogs.createAddTaskDialog(builder, factory);
        }

        else if (id == Constants.MAIN_SETTING_DIALOG_ID) {
            //dialogs.createGlobelSettingDialog(builder, factory);

            return builder.create();
        } else if (id >= Constants.TASK_SETTING_DIALOG_ID && id < 100) {
            //dialogs.createTaskSettingDialog(bundle, builder, factory);

            return builder.create();
        }
        return super.onCreateDialog(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.getHelper().close();

    }

}
