package cn.com.spinachzzz.spinachuncle.vo;

import java.io.Serializable;
import java.util.Date;

import cn.com.spinachzzz.spinachuncle.domain.Tasks;

/**
 * Created by Jing on 25/03/15.
 */
public class TaskParamVO implements Serializable{

    private Tasks task;
    private Date date;


    public Tasks getTask() {
        return task;
    }

    public void setTask(Tasks task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
