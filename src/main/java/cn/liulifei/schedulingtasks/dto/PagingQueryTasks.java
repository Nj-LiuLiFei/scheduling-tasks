package cn.liulifei.schedulingtasks.dto;

public class PagingQueryTasks extends PagingQueryBase {
    private String taskName;
    private short state;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }
}
