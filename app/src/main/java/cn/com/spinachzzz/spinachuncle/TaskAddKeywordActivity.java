package cn.com.spinachzzz.spinachuncle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import net.rdrei.android.dirchooser.DirectoryChooserActivity;

import java.io.File;

import cn.com.spinachzzz.spinachuncle.dao.DatabaseHelper;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.util.StringUtils;
import cn.com.spinachzzz.spinachuncle.vo.TaskType;


public class TaskAddKeywordActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    public static final String TAG = TaskAddKeywordActivity.class.getSimpleName();

    private static final int PICK_REQUEST_CODE = 1;

    private EditText nameEditText;

    private TextView savePathTv;

    private Button browseBtn;

    private EditText urlEditText;

    private EditText beforeEditText;

    private EditText startsWithEditText;

    private EditText endsWithEditText;

    private EditText afterEditText;

    private Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add_keyword);

        setElements();

        initAddBtn();
    }

    private void setElements() {
        nameEditText = (EditText) this.findViewById(R.id.task_keyword_name);
        savePathTv = (TextView) this.findViewById(R.id.task_keyword_save_path_tv);
        browseBtn = (Button) this.findViewById(R.id.task_keyword_browse_btn);
        urlEditText = (EditText) this.findViewById(R.id.task_keyword_url);
        beforeEditText = (EditText) this.findViewById(R.id.task_keyword_before);
        startsWithEditText = (EditText) this.findViewById(R.id.task_keyword_starts_with);
        endsWithEditText = (EditText) this.findViewById(R.id.task_keyword_ends_with);
        afterEditText = (EditText) this.findViewById(R.id.task_keyword_after);

        addBtn = (Button) this.findViewById(R.id.task_keyword_add_btn);
    }

    private void initAddBtn() {

        browseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final Intent chooserIntent = new Intent(TaskAddKeywordActivity.this, DirectoryChooserActivity.class);

// Optional: Allow users to create a new directory with a fixed name.
                chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_NEW_DIR_NAME,
                        "DirChooserSample");

// REQUEST_DIRECTORY is a constant integer to identify the request, e.g. 0
                startActivityForResult(chooserIntent, PICK_REQUEST_CODE);
            }

        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tasks tasks = new Tasks();
                tasks.setCode(StringUtils.createUUID());
                tasks.setLabel(nameEditText.getText().toString());
                tasks.setTaskType(TaskType.KEYWORD);
                tasks.setSavePath("aaa");
                tasks.setTargetUrl(urlEditText.getText().toString());
                tasks.setKeywordBf(beforeEditText.getText().toString());
                tasks.setKeywordSw(startsWithEditText.getText().toString());
                tasks.setKeywordEw(endsWithEditText.getText().toString());
                tasks.setKeywordAf(afterEditText.getText().toString());

                TaskAddKeywordActivity.this.getHelper().getTasksDao().create(tasks);

                Intent intent = new Intent();
                intent.setClass(TaskAddKeywordActivity.this,
                        MainActivity.class);
                TaskAddKeywordActivity.this.startActivity(intent);
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
        getMenuInflater().inflate(R.menu.menu_task_add_keyword, menu);
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
