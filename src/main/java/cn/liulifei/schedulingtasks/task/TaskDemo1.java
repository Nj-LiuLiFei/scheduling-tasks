package cn.liulifei.schedulingtasks.task;

import cn.liulifei.schedulingtasks.entity.TaskEntity;
import cn.liulifei.schedulingtasks.listener.ApplicationStartup;
import cn.liulifei.schedulingtasks.service.ExecuteInfoService;
import cn.liulifei.schedulingtasks.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class TaskDemo1 implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(TaskDemo1.class);

    private static ExecuteInfoService executeInfoService;

    public TaskDemo1(){
        executeInfoService = (ExecuteInfoService) ApplicationStartup.getApplicationContext().getBean("executeInfoService");
    }

    @Override
    public void run() {

        logger.info("TaskDemo1--开始了");
        int executeInfoId;
        executeInfoId = executeInfoService.insert(this.getClass().getName());
        try {
            Thread.sleep(2000);
            executeInfoService.update(executeInfoId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("TaskDemo1--完成了");

    }
}
