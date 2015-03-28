package cn.com.spinachzzz.spinachuncle.util;

import android.util.Log;
import android.widget.DatePicker;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import cn.com.spinachzzz.spinachuncle.Constants;

/**
 * Created by Jing on 13/03/15.
 */
public class CommonUtils {

    public static final String TAG = CommonUtils.class.getSimpleName();


    public static void renameFromTmp(File file) {
        if (file.isFile() && file.getName().endsWith(Constants.TMP_EXT)) {
            int pos = file.getName().lastIndexOf(Constants.TMP_EXT);
            if (pos > 0) {
                String newName = file.getName().substring(0, pos);
                file.renameTo(new File(file.getParentFile().getAbsolutePath() + "/" + newName));
            }
        }
    }

    public static void forceMkdir(File file) {

        if (!file.getParentFile().exists()) {
            try {
                FileUtils.forceMkdir(file.getParentFile());
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }

    }

    public static void trySetCalendarShow(DatePicker datePicker, boolean show){
        int currentApiVersionVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersionVersion >= 11) {
            try {
                Method m = datePicker.getClass().getMethod("setCalendarViewShown", boolean.class);
                m.invoke(datePicker, show);
            }
            catch (Exception e) {}
        }
    }
}
