package cn.com.spinachzzz.spinachuncle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ToggleButton;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.HashMap;
import java.util.Map;

import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.service.SingleTaskService;

public class MainActivityDialogs {

    private MainActivity mainActivity;

    public MainActivityDialogs(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

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
                        TaskAddKeywordActivity.class);
                mainActivity.startActivity(intent);
            }
        });


        RadioButton dateCalcRtn = (RadioButton) mainAddTaskDialogView.findViewById(R.id.main_radio_date_calc);

        dateCalcRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                intent.setClass(mainActivity,
                        TaskAddDateCalcActivity.class);
                mainActivity.startActivity(intent);
            }
        });

        return builder.create();
    }

    /**
     * public void createGlobelSettingDialog(AlertDialog.Builder builder,
     * LayoutInflater factory) {
     * <p/>
     * final GlobelSettings globelSetting = mainActivity.getGlobelSetting();
     * final ConfigurationDao configDao = mainActivity.getConfigDao();
     * <p/>
     * final View mainSettingView = factory.inflate(R.layout.main_setting,
     * null);
     * <p/>
     * final TimePicker scheduleTime = (TimePicker) mainSettingView
     * .findViewById(R.id.main_setting_schedule_date);
     * final ToggleButton autoWifi = (ToggleButton) mainSettingView
     * .findViewById(R.id.main_setting_auto_wifi_toggle);
     * final ToggleButton wifiOnly = (ToggleButton) mainSettingView
     * .findViewById(R.id.main_setting_wifi_only_toggle);
     * <p/>
     * Log.i(MainActivity.TAG, globelSetting.toString());
     * <p/>
     * scheduleTime.setCurrentHour(globelSetting.getScheduleHour());
     * scheduleTime.setCurrentMinute(globelSetting.getScheduleMin());
     * autoWifi.setChecked(globelSetting.getAutoConnect());
     * wifiOnly.setChecked(globelSetting.getOnlyWifi());
     * <p/>
     * builder.setIcon(R.drawable.setting);
     * builder.setTitle(R.string.settings);
     * builder.setView(mainSettingView);
     * builder.setPositiveButton(R.string.apply,
     * new DialogInterface.OnClickListener() {
     * public void onClick(DialogInterface dialog, int whichButton) {
     * globelSetting.setScheduleTime(
     * scheduleTime.getCurrentHour(),
     * scheduleTime.getCurrentMinute());
     * globelSetting.setAutoConnect(autoWifi.isChecked());
     * globelSetting.setOnlyWifi(wifiOnly.isChecked());
     * <p/>
     * configDao.updateGlobelSetting(globelSetting);
     * //mainActivity.startTimeTask();
     * }
     * });
     * <p/>
     * }
     */

    public AlertDialog createTaskDialog(Bundle bundle,
                                        AlertDialog.Builder builder, LayoutInflater factory) {

        final RuntimeExceptionDao<Tasks, String> taskDAO = mainActivity.getTaskRuntimeDao();

        String code = bundle.getString("code");

        Log.i(MainActivity.TAG, "code:" + code);

        final Tasks task = taskDAO.queryForId(code);

        final View taskDialogView = factory.inflate(R.layout.main_list_task,
                null);
        builder.setView(taskDialogView);

        final Button tastStartBtn = (Button) taskDialogView
                .findViewById(R.id.task_start);

        tastStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(MainActivity.TAG, task.toString());

                Intent intent = new Intent();
                intent.setClass(mainActivity, SingleTaskService.class);
                intent.putExtra("task", task);

                mainActivity.startService(intent);

                mainActivity.dismissDialog(Constants.TASK_DIALOG_ID);

            }
        });

        AlertDialog dialog =  builder.create();
        return dialog;
    }

}
