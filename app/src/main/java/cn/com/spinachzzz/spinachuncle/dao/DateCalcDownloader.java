package cn.com.spinachzzz.spinachuncle.dao;

import android.util.Log;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Date;

import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.exception.MessageException;
import cn.com.spinachzzz.spinachuncle.util.StringUtils;
import cn.com.spinachzzz.spinachuncle.vo.TaskParamVO;

public class DateCalcDownloader extends BaseDownloader {

    public static final String TAG = DateCalcDownloader.class.getSimpleName();

    public DateCalcDownloader(TaskParamVO taskParam) {
        this.taskParam = taskParam;
    }

    private Date getWorkDate() {
        if (taskParam.getDate() != null) {
            return taskParam.getDate();
        }

        return new Date();
    }

    @Override
    protected void download() throws Exception {
        Tasks tasks = taskParam.getTask();

        String url = StringUtils.replaceWithDateFormat(getWorkDate(), tasks.getTargetUrl());

        Log.i(TAG, url);

        String fileName = FilenameUtils.getName(url);

        String saveFilePath = tasks.getSavePath() + "/" + fileName;
        File saveFile = new File(saveFilePath);

        if (!saveFile.exists() && !saveFile.isDirectory()) {
            super.downFile(url, saveFile, tasks.getLabel() + " is downloading ");

        } else {
            throw new MessageException("File " + saveFile.getAbsolutePath() + " already exist.");
        }
    }

}
