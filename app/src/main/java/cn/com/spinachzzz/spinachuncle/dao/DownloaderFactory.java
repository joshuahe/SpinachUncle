package cn.com.spinachzzz.spinachuncle.dao;

import cn.com.spinachzzz.spinachuncle.Constants;
import cn.com.spinachzzz.spinachuncle.domain.Tasks;
import cn.com.spinachzzz.spinachuncle.vo.TaskParamVO;
import cn.com.spinachzzz.spinachuncle.vo.TaskType;

public class DownloaderFactory {

    public static BaseDownloader createDownloader(TaskParamVO taskParam) {
        BaseDownloader baseDownloader = null;

        if (taskParam.getTask().getTaskType() == TaskType.KEYWORD) {
            baseDownloader = new KeywordDownloader(taskParam);

        }

        else if(taskParam.getTask().getTaskType() == TaskType.DATE_CALC){
            baseDownloader = new DateCalcDownloader(taskParam);

        }
        return baseDownloader;
    }

}
