package cn.liulifei.schedulingtasks.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskDemo2 implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(TaskDemo2.class);

    @Override
    public void run() {
        logger.info("TaskDemo2--执行了");
    }
}
