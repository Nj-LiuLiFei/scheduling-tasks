package cn.liulifei.schedulingtasks.service;

import cn.liulifei.schedulingtasks.dto.TaskDto;
import cn.liulifei.schedulingtasks.entity.ExecuteInfoEntity;

public interface ExecuteInfoService {
    int insert(String className);
    int update(int id);
}
