package cn.liulifei.schedulingtasks.service.impl;

import cn.liulifei.schedulingtasks.dto.TaskDto;
import cn.liulifei.schedulingtasks.entity.TaskEntity;
import cn.liulifei.schedulingtasks.mapper.TaskMapper;
import cn.liulifei.schedulingtasks.service.TaskService;
import cn.liulifei.schedulingtasks.task.scheduler.TaskSchedulerService;
import cn.liulifei.schedulingtasks.util.LocalDateTimeUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    @Qualifier("taskMapper")
    private TaskMapper taskMapper;

    @Override
    public int insert(TaskDto taskDto) throws Exception {
        TaskEntity taskEntity = taskMapper.getIdNameByClassName(taskDto.getcName());
        if(taskEntity != null){
            throw new Exception("添加失败！(该类已存在)");
        }
        taskEntity = new TaskEntity();
        taskEntity.setClassName(taskDto.getcName());
        taskEntity.setExecutionTime(taskDto.getExecutionTime());
        taskEntity.setTaskName(taskDto.getName());
        taskEntity.setTaskDescribe(taskDto.getDescribe());
        taskEntity.setCreateTime(LocalDateTimeUtils.getNowDateTime());
        return taskMapper.insert(taskEntity);
    }

    @Override
    public int delete(int id) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        taskEntity.setModifiedTime(LocalDateTimeUtils.getNowDateTime());
        return taskMapper.delete(taskEntity);
    }

    @Override
    public int updateTask(TaskDto taskDto) throws Exception {
        Runnable task = TaskSchedulerService.get(taskDto.getId());
        if(task != null) {
            throw new Exception("添加失败！(任务已在队列，请停用后修改)");
        }
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskDto.getId());
        taskEntity.setClassName(taskDto.getcName());
        taskEntity.setTaskName(taskDto.getName());
        taskEntity.setExecutionTime(taskDto.getExecutionTime());
        taskEntity.setTaskDescribe(taskDto.getDescribe());
        taskEntity.setModifiedTime(LocalDateTimeUtils.getNowDateTime());
        return taskMapper.updateTask(taskEntity);
    }

    @Override
    public Page<TaskEntity> pagingQueryTasks(int currentPage, int pageSize, TaskEntity taskEntity) {
        PageHelper.startPage(currentPage, pageSize);
        List<TaskEntity> taskEntityList = taskMapper.pagingQueryTasks(taskEntity);
        Page<TaskEntity> taskEntityPage = (Page<TaskEntity>) taskEntityList;
        return taskEntityPage;
    }

    @Override
    public TaskEntity getTaskClassNameTaskExecutionTimeById(int id) {
        if(id == 0) {
            return null;
        }
        return taskMapper.getClassNameExecutionTimeById(id);
    }

    @Override
    public TaskEntity getTaskDetail(int id) {
        return taskMapper.getIdClassNameExecutionTimeNameDescribeById(id);
    }

    @Override
    @Transactional
    public void enabledTask(int id) throws Exception {
        TaskEntity taskEntity = this.validationData(id);
        this.executeTask(taskEntity.getId(),taskEntity.getClassName(),taskEntity.getExecutionTime());
        taskEntity.setModifiedTime(LocalDateTimeUtils.getNowDateTime());
        taskMapper.updateTaskStateModifiedTime(taskEntity);
    }

    @Override
    public boolean stopTask(int id) throws Exception {
        Runnable task = TaskSchedulerService.get(id);
        if(task == null) {
            throw new Exception("停用失败！(该任务不在任务队列中)");
        }
        taskMapper.updateTaskState(id);
        return TaskSchedulerService.stop(id);
    }

    @Override
    public void manualExecute(int id) throws Exception {
        TaskEntity taskEntity = this.validationData(id);
        this.executeTask(taskEntity.getId(),taskEntity.getClassName(),null);
        taskEntity.setModifiedTime(LocalDateTimeUtils.getNowDateTime());
        taskMapper.updateTaskStateModifiedTime(taskEntity);
    }

    @Override
    public TaskEntity getIdNameByClassName(String className) {
        return taskMapper.getIdNameByClassName(className);
    }

    private TaskEntity validationData(int id) throws Exception {
        TaskEntity taskEntity = this.getTaskClassNameTaskExecutionTimeById(id);
        if(taskEntity == null || StringUtils.isEmpty(taskEntity.getClassName())) {
            throw new Exception("启用失败！(任务数据错误)");
        }
        if(taskEntity.getState() == 1 || taskEntity.getState() == 2){
            throw new Exception("启用失败！(只有已停止的任务才能启用)");
        }
        return  taskEntity;
    }
    private void executeTask(int key,String className,String trigger) throws Exception {
        Runnable task = TaskSchedulerService.get(key);
        if(task !=null ){
            throw new Exception("启用失败！(该任务已在任务队列中)");
        }
        task = (Runnable) BeanUtils.instantiateClass(Class.forName(className));
        if(trigger == null){
            TaskSchedulerService.submit(task);
        }else {
            TaskSchedulerService.add(key,task,trigger);
        }
    }
}
