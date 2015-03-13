package cn.com.spinachzzz.spinachuncle.dao;

import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.vo.TaskType;

public class DownloaderFactory {

    public static BaseDownloader createDownloader(Tasks task) {
        BaseDownloader baseDownloader = null;

        if (task.getTaskType() == TaskType.KEYWORD) {
            baseDownloader = new KeywordDownloader(task);

        }

        else if(task.getTaskType() == TaskType.DATE_CALC){
            baseDownloader = new DateCalcDownloader(task);

        }
        return baseDownloader;
    }

}
