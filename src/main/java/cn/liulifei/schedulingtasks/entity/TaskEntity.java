package cn.liulifei.schedulingtasks.entity;

import java.util.Date;

public class TaskEntity {
    private Integer id;
    private String className;
    private String executionTime;
    private String taskName;
    private String taskDescribe;
    private int state;
    private int deleted;
    private String createTime;
    private String modifiedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescribe() {
        return taskDescribe;
    }

    public void setTaskDescribe(String taskDescribe) {
        this.taskDescribe = taskDescribe;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", executionTime='" + executionTime + '\'' +
                ", taskName='" + taskName + '\'' +
                ", taskDescribe='" + taskDescribe + '\'' +
                ", state=" + state +
                ", deleted=" + deleted +
                ", createTime='" + createTime + '\'' +
                ", modifiedTime='" + modifiedTime + '\'' +
                '}';
    }
}
