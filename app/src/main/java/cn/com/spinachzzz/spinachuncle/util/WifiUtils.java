package cn.com.spinachzzz.spinachuncle.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

public class WifiUtils {
    private static String LOCK_NAME = "SPIN_WIFI_LOCK";
    public static WifiLock wifiLock;

    public static boolean checkOnlineState(Context context, boolean wifiOnly) {
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        State state = conManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        if (state != null && state == State.CONNECTED) {
            conManager.setNetworkPreference(ConnectivityManager.TYPE_WIFI);
            return true;

        }

        state = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        if ((!wifiOnly) && state != null
                && state == State.CONNECTED) {
            return true;
        }

        return false;

    }

    public static void openWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        wifiLock = wifiManager.createWifiLock(LOCK_NAME);
        wifiLock.setReferenceCounted(false);
        wifiManager.setWifiEnabled(true);

        wifiLock.acquire();

    }

    public static void closeWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(false);

        if (wifiLock != null) {
            wifiLock.release();
            wifiLock = null;
        }
    }
}
