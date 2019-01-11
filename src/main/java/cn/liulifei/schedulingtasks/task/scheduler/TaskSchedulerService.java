package cn.liulifei.schedulingtasks.task.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class TaskSchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerService.class);
    public  enum resultTaskEnum {

    }
    private static final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private static final Map<Integer,ScheduledFuture> taskHashMap = Collections.synchronizedMap(new HashMap<>());
    public TaskSchedulerService(){
        logger.info("构造方法开始");
    }
    public static void init(){
        logger.info("初始化器..");
    }
    public static Runnable get(int key){
        return (Runnable) taskHashMap.get(key);
    }
    public static void add(int key,Runnable task,String trigger){
        Runnable t = get(key);
        if(t == null) {
            ScheduledFuture scheduledFuture = threadPoolTaskScheduler.schedule(task,new CronTrigger(trigger));
            taskHashMap.put(key,scheduledFuture);
        }
    }
    public static boolean stop(int key){
        ScheduledFuture scheduledFuture = taskHashMap.get(key);
        if(scheduledFuture == null) {
            return false;
        }
        taskHashMap.remove(key);
        return scheduledFuture.cancel(true);
    }
    public static void submit(Runnable task){
        threadPoolTaskScheduler.submit(task);
    }
    public static boolean isQueuePoolById(int key){
        return taskHashMap.get(key) != null;
    }
    static {
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.setPoolSize(15);
        logger.info("ThreadPoolTaskScheduler,任务池创建成功！");
    }

}
