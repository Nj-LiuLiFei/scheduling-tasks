package cn.liulifei.schedulingtasks;

import cn.liulifei.schedulingtasks.listener.ApplicationStartup;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.liulifei.schedulingtasks.mapper")
public class SchedulingTasksApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SchedulingTasksApplication.class);
        springApplication.addListeners(new ApplicationStartup());
        springApplication.addInitializers();
        springApplication.run(args);
    }
}
