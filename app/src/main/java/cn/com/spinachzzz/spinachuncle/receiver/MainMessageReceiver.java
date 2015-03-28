package cn.com.spinachzzz.spinachuncle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.com.spinachzzz.spinachuncle.MainActivity;

/**
 * Created by Jing on 28/03/15.
 */
public class MainMessageReceiver extends BroadcastReceiver {
    private MainActivity mainActivity;


    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String message = bundle.getString("message");

        if (mainActivity!=null && message != null) {
            mainActivity.updateMessages(message);
        }

    }
}
