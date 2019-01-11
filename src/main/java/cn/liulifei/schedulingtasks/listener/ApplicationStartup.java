package cn.liulifei.schedulingtasks.listener;


import cn.liulifei.schedulingtasks.task.scheduler.TaskSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
    private static ApplicationContext applicationContext;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("SpringBoot--初始化");
        this.applicationContext =contextRefreshedEvent.getApplicationContext();
        TaskSchedulerService.init();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
