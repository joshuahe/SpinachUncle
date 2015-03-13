package cn.com.spinachzzz.spinachuncle.dao;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Date;

import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.exception.MessageException;
import cn.com.spinachzzz.spinachuncle.util.DateUtils;
import cn.com.spinachzzz.spinachuncle.util.StringUtils;

public class DateCalcDownloader extends BaseDownloader {

    public DateCalcDownloader(Tasks tasks) {
        this.tasks = tasks;
    }

    @Override
    protected void download() throws Exception {
        String url = DateUtils.formatDate(new Date(), tasks.getTargetUrl());
        url = StringUtils.removeBraces(url);

        String fileName = FilenameUtils.getName(url);

        String saveFilePath = tasks.getSavePath() + "/" + fileName;
        File saveFile = new File(saveFilePath);

        if (saveFile.exists() && !saveFile.isDirectory()) {
            super.downFile(url, saveFile, tasks.getLabel() + " is downloading ");

        } else {
            throw new MessageException("File "+saveFile.getAbsolutePath()+" already exist.");
        }
    }

}
