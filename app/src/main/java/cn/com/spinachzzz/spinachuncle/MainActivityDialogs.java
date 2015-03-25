package cn.com.spinachzzz.spinachuncle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.service.SingleTaskService;
import cn.com.spinachzzz.spinachuncle.vo.TaskType;

public class MainActivityDialogs {

    private MainActivity mainActivity;

    public MainActivityDialogs(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    private Map<String, PopupWindow> popupWindowMap = new HashMap<String, PopupWindow>();

    public AlertDialog createAddTaskDialog(AlertDialog.Builder builder,
                                           LayoutInflater factory) {

        final View mainAddTaskDialogView = factory.inflate(R.layout.main_add_task_dialog,
                null);
        builder.setView(mainAddTaskDialogView);

        RadioButton keywordRtn = (RadioButton) mainAddTaskDialogView.findViewById(R.id.main_radio_keyword);

        keywordRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setClass(mainActivity,
                        TaskKeywordActivity.class);
                mainActivity.startActivity(intent);
            }
        });


        RadioButton dateCalcRtn = (RadioButton) mainAddTaskDialogView.findViewById(R.id.main_radio_date_calc);

        dateCalcRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setClass(mainActivity,
                        TaskDateCalcActivity.class);
                mainActivity.startActivity(intent);
            }
        });

        return builder.create();
    }


    public AlertDialog createTaskDialog(Bundle bundle,
                                        AlertDialog.Builder builder, LayoutInflater factory) {

        final RuntimeExceptionDao<Tasks, String> taskDAO = mainActivity.getTaskRuntimeDao();

        String code = bundle.getString("code");

        Log.i(MainActivity.TAG, "code:" + code);

        final Tasks task = taskDAO.queryForId(code);

        final View taskDialogView = factory.inflate(R.layout.main_list_task,
                null);
        builder.setView(taskDialogView);

        initTaskBtn(taskDialogView, task, taskDAO);

        AlertDialog dialog = builder.create();
        return dialog;
    }


    public void createTaskPopup(Bundle bundle,
                                View convertView, LayoutInflater factory) {

        final RuntimeExceptionDao<Tasks, String> taskDAO = mainActivity.getTaskRuntimeDao();

        String code = bundle.getString("code");

        final Tasks task = taskDAO.queryForId(code);

        PopupWindow popupWindow = popupWindowMap.get(task.getCode());
        if (popupWindow == null) {
            final View taskDialogView = factory.inflate(R.layout.main_list_task,
                    null);

            initTaskBtn(taskDialogView, task, taskDAO);

            popupWindow = new PopupWindow(taskDialogView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.update();
            popupWindow.setBackgroundDrawable(new BitmapDrawable());

            popupWindowMap.put(task.getCode(), popupWindow);
        }

        popupWindow.showAtLocation(convertView, Gravity.TOP, 0, convertView.getTop() + 200);

    }

    private void initTaskBtn(View taskDialogView, final Tasks task, final RuntimeExceptionDao<Tasks, String> taskDAO) {
        final Button taskStartBtn = (Button) taskDialogView
                .findViewById(R.id.task_start);
        final Button taskEditBth = (Button) taskDialogView.findViewById(R.id.task_edit);
        final Button taskDeleteBth = (Button) taskDialogView.findViewById(R.id.tast_delete);
        final Button taskOpenBth = (Button) taskDialogView.findViewById(R.id.task_open);

        taskStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(mainActivity, SingleTaskService.class);
                intent.putExtra("task", task);

                mainActivity.startService(intent);

                dismissPopupWindow(task);

            }
        });

        taskEditBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (task.getTaskType() == TaskType.KEYWORD) {

                    Intent intent = new Intent();
                    intent.setClass(mainActivity, TaskKeywordActivity.class);
                    intent.putExtra("task", task);

                    mainActivity.startActivity(intent);

                }
                else if(task.getTaskType() == TaskType.DATE_CALC){
                    Intent intent = new Intent();
                    intent.setClass(mainActivity, TaskDateCalcActivity.class);
                    intent.putExtra("task", task);

                    mainActivity.startActivity(intent);
                }

            }
        });

        taskDeleteBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDAO.deleteById(task.getCode());

                dismissPopupWindow(task);

                mainActivity.finish();
                mainActivity.startActivity(mainActivity.getIntent());
            }
        });

        taskOpenBth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri startDir = Uri.fromFile(new File(task.getSavePath()));
                Intent intent = new Intent();
                intent.setDataAndType(startDir, "resource/folder");
                intent.setAction(Intent.ACTION_VIEW);
                mainActivity.startActivity(intent);
            }
        });
    }

    private void dismissPopupWindow(Tasks task) {
        PopupWindow popupWindow = popupWindowMap.get(task.getCode());
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
