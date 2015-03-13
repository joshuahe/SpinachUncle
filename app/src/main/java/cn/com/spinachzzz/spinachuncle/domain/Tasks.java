package cn.com.spinachzzz.spinachuncle.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import cn.com.spinachzzz.spinachuncle.vo.TaskType;

/**
 * Created by Jing on 11/03/15.
 */

@DatabaseTable(tableName = "TASKS")
public class Tasks implements Serializable{

    @DatabaseField(id = true)
    private String code;

    @DatabaseField
    private String label;

    @DatabaseField
    private TaskType taskType;

    @DatabaseField
    private String savePath;

    @DatabaseField
    private Integer maxKeep;

    @DatabaseField
    private String targetUrl;

    @DatabaseField
    private String keywordBf;

    @DatabaseField
    private String keywordSw;

    @DatabaseField
    private String keywordEw;

    @DatabaseField
    private String keywordAf;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Integer getMaxKeep() {
        return maxKeep;
    }

    public void setMaxKeep(Integer maxKeep) {
        this.maxKeep = maxKeep;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getKeywordBf() {
        return keywordBf;
    }

    public void setKeywordBf(String keywordBf) {
        this.keywordBf = keywordBf;
    }

    public String getKeywordSw() {
        return keywordSw;
    }

    public void setKeywordSw(String keywordSw) {
        this.keywordSw = keywordSw;
    }

    public String getKeywordEw() {
        return keywordEw;
    }

    public void setKeywordEw(String keywordEw) {
        this.keywordEw = keywordEw;
    }

    public String getKeywordAf() {
        return keywordAf;
    }

    public void setKeywordAf(String keywordAf) {
        this.keywordAf = keywordAf;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                ", taskType=" + taskType +
                ", savePath='" + savePath + '\'' +
                ", maxKeep=" + maxKeep +
                ", targetUrl='" + targetUrl + '\'' +
                ", keywordBf='" + keywordBf + '\'' +
                ", keywordSw='" + keywordSw + '\'' +
                ", keywordEw='" + keywordEw + '\'' +
                ", keywordAf='" + keywordAf + '\'' +
                '}';
    }
}

