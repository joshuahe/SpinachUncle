package cn.com.spinachzzz.spinachuncle.util;

import java.io.File;

import cn.com.spinachzzz.spinachuncle.Constants;

/**
 * Created by Jing on 13/03/15.
 */
public class CommonUtils {

    public static void renameFromTmp(File file) {
        if (file.isFile() && file.getName().endsWith(Constants.TMP_EXT)) {
            int pos = file.getName().lastIndexOf(Constants.TMP_EXT);
            if (pos > 0) {
                String newName = file.getName().substring(0, pos);
                file.renameTo(new File(file.getParentFile().getAbsolutePath() + "/" + newName));
            }
        }
    }
}
