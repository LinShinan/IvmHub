package com.stone.manage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.TaskDetailsMapper;
import com.stone.manage.domain.TaskDetails;
import com.stone.manage.service.ITaskDetailsService;

/**
 * 工单详情Service业务层处理
 * 
 * @author stone
 * @date 2026-04-20
 */
@Service
public class TaskDetailsServiceImpl implements ITaskDetailsService 
{
    @Autowired
    private TaskDetailsMapper taskDetailsMapper;

    /**
     * 查询工单详情
     * 
     * @param detailsId 工单详情主键
     * @return 工单详情
     */
    @Override
    public TaskDetails selectTaskDetailsByDetailsId(Long detailsId)
    {
        return taskDetailsMapper.selectTaskDetailsByDetailsId(detailsId);
    }

    /**
     * 查询工单详情列表
     * 
     * @param taskDetails 工单详情
     * @return 工单详情
     */
    @Override
    public List<TaskDetails> selectTaskDetailsList(TaskDetails taskDetails)
    {
        return taskDetailsMapper.selectTaskDetailsList(taskDetails);
    }

    /**
     * 新增工单详情
     * 
     * @param taskDetails 工单详情
     * @return 结果
     */
    @Override
    public int insertTaskDetails(TaskDetails taskDetails)
    {
        return taskDetailsMapper.insertTaskDetails(taskDetails);
    }

    /**
     * 修改工单详情
     * 
     * @param taskDetails 工单详情
     * @return 结果
     */
    @Override
    public int updateTaskDetails(TaskDetails taskDetails)
    {
        return taskDetailsMapper.updateTaskDetails(taskDetails);
    }

    /**
     * 批量删除工单详情
     * 
     * @param detailsIds 需要删除的工单详情主键
     * @return 结果
     */
    @Override
    public int deleteTaskDetailsByDetailsIds(Long[] detailsIds)
    {
        return taskDetailsMapper.deleteTaskDetailsByDetailsIds(detailsIds);
    }

    /**
     * 删除工单详情信息
     * 
     * @param detailsId 工单详情主键
     * @return 结果
     */
    @Override
    public int deleteTaskDetailsByDetailsId(Long detailsId)
    {
        return taskDetailsMapper.deleteTaskDetailsByDetailsId(detailsId);
    }

    /**
     * 根据taskId查询工单详情
     * @param taskId
     * @return
     */
    @Override
    public List<TaskDetails> getDetailsByTaskId(Long taskId) {
        TaskDetails param = new TaskDetails();
        param.setTaskId(taskId);
        return taskDetailsMapper.selectTaskDetailsList(param);
    }
}
