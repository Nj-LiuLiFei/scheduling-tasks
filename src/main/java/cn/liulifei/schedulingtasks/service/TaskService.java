package cn.liulifei.schedulingtasks.service;

import cn.liulifei.schedulingtasks.dto.TaskDto;
import cn.liulifei.schedulingtasks.entity.TaskEntity;
import com.github.pagehelper.Page;

import java.util.List;


public interface TaskService {
    int insert(TaskDto taskDto) throws Exception;
    int delete(int id);
    Page<TaskEntity> pagingQueryTasks(int currentPage, int pageSize, TaskEntity taskEntity);
    TaskEntity getTaskClassNameTaskExecutionTimeById(int id);
    int updateTask(TaskDto taskDto) throws Exception;
    TaskEntity getTaskDetail(int id);
    void enabledTask(int id) throws Throwable;
    boolean stopTask(int id) throws Exception;
    void manualExecute(int id) throws Exception;
    TaskEntity getIdNameByClassName(String className);
}
