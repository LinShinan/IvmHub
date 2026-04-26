package com.stone.manage.mapper;

import java.util.List;
import com.stone.manage.domain.Task;
import com.stone.manage.domain.TaskDetails;
import com.stone.manage.domain.VO.TaskVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 工单Mapper接口
 * 
 * @author stone
 * @date 2026-04-20
 */
public interface TaskMapper 
{
    /**
     * 查询工单
     * 
     * @param taskId 工单主键
     * @return 工单
     */
    public Task selectTaskByTaskId(Long taskId);


    /**
     * 新增工单
     * 
     * @param task 工单
     * @return 结果
     */
    public int insertTask(Task task);

    /**
     * 修改工单
     * 
     * @param task 工单
     * @return 结果
     */
    public int updateTask(Task task);

    /**
     * 删除工单
     * 
     * @param taskId 工单主键
     * @return 结果
     */
    public int deleteTaskByTaskId(Long taskId);

    /**
     * 批量删除工单
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskByTaskIds(Long[] taskIds);

    /**
     * 查询工单列表
     *
     * @param task 工单
     * @return 工单集合
     */
    public List<TaskVO> selectTaskVOList(Task task);

    /**
     * 根据机器InnerCode和产品类型ID,工单状态查询工单数量
     * @param innerCode
     * @param productTypeId
     * @param taskStatus
     * @return
     */
    @Select("select count(*) from tb_task where inner_code = #{innerCode} and product_type_id = #{productTypeId} and task_status = #{taskStatus}")
    int countByProductTypeId(@Param("innerCode") String innerCode, @Param("productTypeId") Integer productTypeId, @Param("taskStatus") Long taskStatus);


}
