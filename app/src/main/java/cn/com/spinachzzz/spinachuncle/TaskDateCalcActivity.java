package cn.com.spinachzzz.spinachuncle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;

import cn.com.spinachzzz.spinachuncle.dao.DatabaseHelper;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.util.StringUtils;
import cn.com.spinachzzz.spinachuncle.vo.TaskType;

public class TaskDateCalcActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    public static final String TAG = TaskDateCalcActivity.class.getSimpleName();

    private static final int PICK_REQUEST_CODE = 1;

    private EditText nameEditText;

    private TextView savePathTv;

    private Button browseBtn;

    private EditText urlEditText;

    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_date_calc);

        Tasks task = null;
        if (this.getIntent().getExtras() != null) {
            task = (Tasks) this.getIntent().getExtras().get("task");
        }

        setElements();

        setElementsValues(task);

        initBtns(task);

    }

    private void setElements() {
        nameEditText = (EditText) this.findViewById(R.id.task_date_calc_name);
        savePathTv = (TextView) this.findViewById(R.id.task_date_calc_save_path_tv);
        browseBtn = (Button) this.findViewById(R.id.task_date_calc_browse_btn);
        urlEditText = (EditText) this.findViewById(R.id.task_date_calc_url);

        saveBtn = (Button) this.findViewById(R.id.task_date_calc_save_btn);
    }

    private void setElementsValues(Tasks task) {
        if (task != null) {
            nameEditText.setText(task.getLabel());
            savePathTv.setText(task.getSavePath());
            urlEditText.setText(task.getTargetUrl());

        }
    }


    private void initBtns(final Tasks task) {

        browseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Intent chooserIntent = new Intent(TaskDateCalcActivity.this, DirectoryChooserActivity.class);

                chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_NEW_DIR_NAME,
                        "DirChooserSample");

                startActivityForResult(chooserIntent, PICK_REQUEST_CODE);
            }

        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (task == null) {
                    Tasks tasks = new Tasks();
                    tasks.setCode(StringUtils.createUUID());
                    tasks.setLabel(nameEditText.getText().toString());
                    tasks.setTaskType(TaskType.DATE_CALC);
                    tasks.setSavePath(savePathTv.getText().toString());
                    tasks.setTargetUrl(urlEditText.getText().toString());

                    TaskDateCalcActivity.this.getHelper().getTasksDao().create(tasks);
                } else {
                    task.setLabel(nameEditText.getText().toString());

                    task.setSavePath(savePathTv.getText().toString());
                    task.setTargetUrl(urlEditText.getText().toString());

                    TaskDateCalcActivity.this.getHelper().getTasksDao().update(task);
                }


                Intent intent = new Intent();
                intent.setClass(TaskDateCalcActivity.this,
                        MainActivity.class);
                TaskDateCalcActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_REQUEST_CODE) {
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED) {
                Log.i(TAG, data
                        .getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR));

                savePathTv.setText(data
                        .getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR));
            } else {
                // Nothing selected
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_add_date_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
