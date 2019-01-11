package cn.liulifei.schedulingtasks.service.impl;

import cn.liulifei.schedulingtasks.entity.ExecuteInfoEntity;
import cn.liulifei.schedulingtasks.entity.TaskEntity;
import cn.liulifei.schedulingtasks.mapper.ExecuteInfoMapper;
import cn.liulifei.schedulingtasks.service.ExecuteInfoService;
import cn.liulifei.schedulingtasks.service.TaskService;
import cn.liulifei.schedulingtasks.util.LocalDateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

@Service("executeInfoService")
public class ExecuteInfoServiceImpl implements ExecuteInfoService {

    @Autowired
    @Qualifier("executeInfoMapper")
    private ExecuteInfoMapper executeInfoMapper;

    @Autowired
    @Qualifier("taskService")
    private TaskService taskService;

    @Override
    public int insert(String className) {
        TaskEntity taskEntity = taskService.getIdNameByClassName(className);
        ExecuteInfoEntity executeInfoEntity = new ExecuteInfoEntity();
        executeInfoEntity.setTaskName(taskEntity.getTaskName());
        executeInfoEntity.setStartTime(LocalDateTimeUtils.getNowDateTime());
        executeInfoMapper.insert(executeInfoEntity);
        return executeInfoEntity.getId();
    }

    @Override
    public int update(int id) {
        ExecuteInfoEntity executeInfoEntity = new ExecuteInfoEntity();
        executeInfoEntity.setId(id);
        executeInfoEntity.setEndTime(LocalDateTimeUtils.getNowDateTime());
        return executeInfoMapper.update(executeInfoEntity);
    }
}
