package cn.liulifei.schedulingtasks.enump;

public enum TaskStateEnum {

    WAIT("等待",1),
    EXECUTION("正在执行",2),
    STOP("已停止",3);

    private final  String stateName;
    private final  int state;
    TaskStateEnum(String stateName,int state){
        this.stateName = stateName;
        this.state = state;
    }
    public static String getStateName(int state){
        for (TaskStateEnum c : TaskStateEnum.values()) {
            if (c.getState() == state) {
                return c.getStateName();
            }
        }
        return "未知状态";
    }

    public int getState() {
        return state;
    }
    public String getStateName(){
        return stateName;
    }
}
