package cn.liulifei.schedulingtasks.controller;

import cn.liulifei.schedulingtasks.dto.TaskDto;
import cn.liulifei.schedulingtasks.entity.TaskEntity;
import cn.liulifei.schedulingtasks.enump.TaskStateEnum;
import cn.liulifei.schedulingtasks.dto.PagingQueryTasks;
import cn.liulifei.schedulingtasks.service.TaskService;
import cn.liulifei.schedulingtasks.task.scheduler.TaskSchedulerService;
import cn.liulifei.schedulingtasks.util.LocalDateTimeUtils;
import cn.liulifei.schedulingtasks.vo.PagingResultVo;
import cn.liulifei.schedulingtasks.vo.TaskDetailVO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    @Qualifier("taskService")
    private TaskService taskService;

    @PostMapping(value = "add")
    public ResponseEntity insert(TaskDto taskDto){
        Map map = new HashMap<String,Object>();
        HttpStatus httpStatus;
        try {
            taskService.insert(taskDto);
            httpStatus = HttpStatus.OK;
            map.put("message","添加成功！");
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            map.put("message",e.getMessage());
        }
        return new ResponseEntity(map,httpStatus);
    }

    @PostMapping(value = "deleteTask")
    public ResponseEntity deleteTask(@RequestParam(value = "id") int id){
        Map map = new HashMap<String,Object>();
        if(TaskSchedulerService.isQueuePoolById(id)){
            map.put("message","删除失败！(该任务已在任务队列里，请先停用后删除)");
            return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        taskService.delete(id);
        map.put("message","删除成功！");
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping(value = "updateTask")
    public ResponseEntity updateTask(TaskDto taskDto){
        Map map = new HashMap<String,Object>();
        HttpStatus httpStatus;
        int row = 0;
        try {
            row = taskService.updateTask(taskDto);
            if(row == 0){
                map.put("message","修改失败！(无法更新信息)");
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }else {
                map.put("message","修改成功！");
                httpStatus = HttpStatus.OK;
            }
            return new ResponseEntity(map,httpStatus);
        } catch (Exception e) {
            map.put("message",e.getMessage());
            return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping(value = "getTaskDetail")
    public ResponseEntity viewTask(@RequestParam("id") int id){
        Map map = new HashMap<String,Object>();
        TaskEntity taskEntity = taskService.getTaskDetail(id);
        if(taskEntity == null) {
            map.put("message","获取信息失败！(找不到对应的信息)");
            return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        TaskDetailVO taskDetailVO = new TaskDetailVO();
        taskDetailVO.setId(taskEntity.getId());
        taskDetailVO.setcName(taskEntity.getClassName());
        taskDetailVO.setName(taskEntity.getTaskName());
        taskDetailVO.setExecutionTime(taskEntity.getExecutionTime());
        taskDetailVO.setDescribe(taskEntity.getTaskDescribe());
        map.put("task",taskDetailVO);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping(value = "pagingQueryTasks")
    public ResponseEntity pagingQueryTasks(PagingQueryTasks pagingQueryTasks){

        TaskEntity taskEntity =new TaskEntity();
        taskEntity.setTaskName(pagingQueryTasks.getTaskName());
        taskEntity.setState(pagingQueryTasks.getState());
        System.out.println(taskEntity);
        Page<TaskEntity> page = taskService.pagingQueryTasks(pagingQueryTasks.getOffset(),pagingQueryTasks.getPageSize(),taskEntity);
        JSONArray jsonArray = new JSONArray();
        for (int i=0;i<page.size();i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",page.get(i).getId());
            jsonObject.put("task_class_name",page.get(i).getClassName());
            jsonObject.put("execution_time",page.get(i).getExecutionTime());
            jsonObject.put("task_name",page.get(i).getTaskName());
            jsonObject.put("task_describe",page.get(i).getTaskDescribe());
            jsonObject.put("task_state",page.get(i).getState());
            jsonObject.put("task_state_text", TaskStateEnum.getStateName(page.get(i).getState()));
            jsonObject.put("create_time",page.get(i).getCreateTime());
            jsonObject.put("update_time",page.get(i).getModifiedTime());
            jsonArray.add(jsonObject);
        }
        return new ResponseEntity(new PagingResultVo(pagingQueryTasks,jsonArray,page.getTotal()),HttpStatus.OK);
    }

    @GetMapping(value = "stopTask")
    public ResponseEntity stopTask(@RequestParam("id") int id){
        Map map = new HashMap<String,Object>();
        try {
            taskService.stopTask(id);
            map.put("message","执行成功！");
            return new ResponseEntity(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message",e.getMessage());
            return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "enabledTask")
    public ResponseEntity enabledTask(@RequestParam("id") int id){
        Map map = new HashMap<String,Object>();
        HttpStatus httpStatus;
        try{
            taskService.enabledTask(id);
            httpStatus = HttpStatus.OK;
            map.put("message","启用成功！");
            return new ResponseEntity(map,httpStatus);
        }catch (ClassNotFoundException e){
            map.put("message","启用失败！(根据类路径找不到执行类)");
        }catch (IllegalArgumentException e){
            map.put("message","启用失败！(任务执行时间格式不合法)");
        }catch (Exception e){
            map.put("message",e.getMessage());
        } catch (Throwable e) {
            map.put("message",e.getMessage());
        }
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity(map,httpStatus);
    }

    @GetMapping(value = "manualExecute")
    public ResponseEntity manualExecute(@RequestParam("id") int id){
        Map map = new HashMap<String,Object>();
        try {
            taskService.manualExecute(id);
            map.put("message","执行成功！");
            return new ResponseEntity(map,HttpStatus.OK);
        } catch (Exception e) {
            map.put("message",e.getMessage());
        }
        return new ResponseEntity(map,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
