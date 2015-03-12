package cn.com.spinachzzz.spinachuncle;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

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

        return builder.create();
    }

    /**
     public void createGlobelSettingDialog(AlertDialog.Builder builder,
     LayoutInflater factory) {

     final GlobelSettings globelSetting = mainActivity.getGlobelSetting();
     final ConfigurationDao configDao = mainActivity.getConfigDao();

     final View mainSettingView = factory.inflate(R.layout.main_setting,
     null);

     final TimePicker scheduleTime = (TimePicker) mainSettingView
     .findViewById(R.id.main_setting_schedule_date);
     final ToggleButton autoWifi = (ToggleButton) mainSettingView
     .findViewById(R.id.main_setting_auto_wifi_toggle);
     final ToggleButton wifiOnly = (ToggleButton) mainSettingView
     .findViewById(R.id.main_setting_wifi_only_toggle);

     Log.i(MainActivity.TAG, globelSetting.toString());

     scheduleTime.setCurrentHour(globelSetting.getScheduleHour());
     scheduleTime.setCurrentMinute(globelSetting.getScheduleMin());
     autoWifi.setChecked(globelSetting.getAutoConnect());
     wifiOnly.setChecked(globelSetting.getOnlyWifi());

     builder.setIcon(R.drawable.setting);
     builder.setTitle(R.string.settings);
     builder.setView(mainSettingView);
     builder.setPositiveButton(R.string.apply,
     new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int whichButton) {
     globelSetting.setScheduleTime(
     scheduleTime.getCurrentHour(),
     scheduleTime.getCurrentMinute());
     globelSetting.setAutoConnect(autoWifi.isChecked());
     globelSetting.setOnlyWifi(wifiOnly.isChecked());

     configDao.updateGlobelSetting(globelSetting);
     //mainActivity.startTimeTask();
     }
     });

     }



     public void createTaskSettingDialog(Bundle bundle,
     AlertDialog.Builder builder, LayoutInflater factory) {

     /**
     final ConfigurationDao configDao = mainActivity.getConfigDao();

     String code = bundle.getString(TaskSettings.CODE);

     Log.i(MainActivity.TAG, "code:" + code);


     final TaskSettings currentTaskSetting = configDao.getTaskSetting(code);

     final View settingView = factory.inflate(R.layout.main_list_setting,
     null);

     Log.i(MainActivity.TAG, currentTaskSetting.toString());
     final ToggleButton scheduleAbleToggleButton = (ToggleButton) settingView
     .findViewById(R.id.main_list_setting_schedule_able_toggle_button);
     scheduleAbleToggleButton.setChecked(currentTaskSetting
     .getScheduleAble());

     final Spinner maxKeepSpinner = (Spinner) settingView
     .findViewById(R.id.main_list_setting_max_keep_spinner);
     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
     mainActivity, R.array.max_keep_array,
     android.R.layout.simple_spinner_item);
     // Specify the layout to use when the list of choices appears
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     maxKeepSpinner.setAdapter(adapter);
     maxKeepSpinner.setSelection(currentTaskSetting.getMaxKeepPos());

     builder.setIcon(R.drawable.setting);
     builder.setTitle(currentTaskSetting.getLabel() + " " + mainActivity.getString(R.string.settings));
     builder.setView(settingView);
     builder.setPositiveButton(R.string.apply,
     new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialog, int whichButton) {
     currentTaskSetting.setScheduleAble(scheduleAbleToggleButton.isChecked());
     currentTaskSetting.setMaxKeepByPos((int) maxKeepSpinner.getSelectedItemId());

     configDao.updateTaskSetting(currentTaskSetting);
     }
     });


     }
     **/

}
