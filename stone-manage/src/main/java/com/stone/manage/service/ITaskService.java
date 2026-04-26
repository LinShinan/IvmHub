package com.stone.manage.service;

import java.util.List;

import com.stone.manage.domain.DTO.TaskDTO;
import com.stone.manage.domain.Task;
import com.stone.manage.domain.VO.TaskVO;

/**
 * 工单Service接口
 * 
 * @author stone
 * @date 2026-04-20
 */
public interface ITaskService 
{
    /**
     * 查询工单
     * 
     * @param taskId 工单主键
     * @return 工单
     */
    public Task selectTaskByTaskId(Long taskId);

    /**
     * 查询工单列表
     *
     * @param task 工单
     * @return 工单集合
     */
    List<TaskVO> selectTaskVOList(Task task);

    /**
     * 新增工单
     * 
     * @param taskDTO 工单
     * @return 结果
     */
    public int insertTaskDTO(TaskDTO taskDTO);

    /**
     * 修改工单
     * 
     * @param task 工单
     * @return 结果
     */
    public int updateTask(Task task);

    /**
     * 批量删除工单
     * 
     * @param taskIds 需要删除的工单主键集合
     * @return 结果
     */
    public int deleteTaskByTaskIds(Long[] taskIds);

    /**
     * 删除工单信息
     * 
     * @param taskId 工单主键
     * @return 结果
     */
    public int deleteTaskByTaskId(Long taskId);

    /**
     * 取消工单
     * @param task
     * @return
     */
    int cancelTask(Task task);


    /**
     * 根据内码和产品类型统计完成的工单数量
     * @param innerCode
     * @param productTypeId
     * @return
     */
    int countFinishedByProductTypeId(String innerCode, Integer productTypeId);
}
