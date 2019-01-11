package cn.liulifei.schedulingtasks.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskDemo3 implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(TaskDemo3.class);

    @Override
    public void run() {
        logger.info("TaskDemo3--执行了");
    }
}
