package cn.liulifei.schedulingtasks.mapper;

import cn.liulifei.schedulingtasks.entity.TaskEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("taskMapper")
public interface TaskMapper {
    int insert(TaskEntity taskEntity);
    int delete(TaskEntity taskEntity);
    int updateTask(TaskEntity taskEntity);
    int updateTaskState(@Param("id")int id);
    List<TaskEntity> pagingQueryTasks(TaskEntity taskEntity);
    TaskEntity getClassNameExecutionTimeById(@Param("id")int id);
    TaskEntity getIdClassNameExecutionTimeNameDescribeById(@Param("id")int id);
    int updateTaskStateModifiedTime(TaskEntity taskEntity);
    TaskEntity getIdNameByClassName(@Param("cName") String cName);
}
