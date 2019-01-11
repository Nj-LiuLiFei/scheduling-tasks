package cn.liulifei.schedulingtasks.config;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@Configurable
public class MybatisConfig {
    @Bean
    public PageHelper pageHelper() {
        System.out.println("MyBatisConfiguration.pageHelper()");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
