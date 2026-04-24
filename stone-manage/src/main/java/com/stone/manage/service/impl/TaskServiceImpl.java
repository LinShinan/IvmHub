package com.stone.manage.service.impl;

import java.time.Duration;
import java.util.List;

import com.stone.common.constant.IvmConstants;
import com.stone.common.utils.DateUtils;
import com.stone.framework.web.exception.BusinessException;
import com.stone.manage.domain.DTO.TaskDTO;
import com.stone.manage.domain.DTO.TaskDetailsDTO;
import com.stone.manage.domain.Emp;
import com.stone.manage.domain.TaskDetails;
import com.stone.manage.domain.VO.TaskVO;
import com.stone.manage.domain.VendingMachine;
import com.stone.manage.mapper.EmpMapper;
import com.stone.manage.mapper.TaskDetailsMapper;
import com.stone.manage.mapper.VendingMachineMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.TaskMapper;
import com.stone.manage.domain.Task;
import com.stone.manage.service.ITaskService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 工单Service业务层处理
 * 
 * @author stone
 * @date 2026-04-20
 */
@Service
public class TaskServiceImpl implements ITaskService 
{
    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private VendingMachineMapper vendingMachineMapper;

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TaskDetailsMapper taskDetailsMapper;

    /**
     * 查询工单
     * 
     * @param taskId 工单主键
     * @return 工单
     */
    @Override
    public Task selectTaskByTaskId(Long taskId)
    {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    /**
     * 查询工单列表
     *
     * @param task 工单
     * @return 工单
     */
    @Override
    public List<TaskVO> selectTaskVOList(Task task)
    {
        return taskMapper.selectTaskVOList(task);
    }

    /**
     * 新增工单
     * 
     * @param taskDTO 工单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTaskDTO(TaskDTO taskDTO)
    {
        //1.查询售货机是否存在
        VendingMachine vm = vendingMachineMapper.selectVendingMachineByInnerCode(taskDTO.getInnerCode());
        if(vm==null){
            throw new BusinessException("售货机不存在");
        }

        //2.查询员工是否存在
        Emp emp = empMapper.selectEmpById(taskDTO.getUserId());
        if(emp==null){
            throw new BusinessException("员工不存在");
        }
        //3.检查员工负责区域是否和售货机区域一致
        if(!emp.getRegionId().equals(vm.getRegionId())) {
            throw new BusinessException("员工负责区域与售货机区域不一致");
        }
        //4.检查售货机状态和工单类型是否符合逻辑
        checkVmStatusAndTaskType(vm.getVmStatus(),taskDTO.getProductTypeId());

        //5.查询该设备是否有未完成的同类工单
        existsSameTypeTask(taskDTO);

        //6.将DTO转换为PO
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO,task);
        task.setTaskStatus(IvmConstants.TASK_STATUS_CREATE);
        task.setRegionId(vm.getRegionId());
        task.setUserName(emp.getUserName());
        task.setAddr(vm.getAddr());
        task.setCreateTime(DateUtils.getNowDate());
        task.setTaskCode(generateTaskCode());//生成工单编号
        int result = taskMapper.insertTask(task);

        //7. 如果是补货工单，添加details
        if(task.getProductTypeId().equals(IvmConstants.TASK_TYPE_SUPPLY)){
            List<TaskDetailsDTO> detailsDTOs = taskDTO.getDetails();
            if(CollectionUtils.isEmpty(detailsDTOs)){
                throw new BusinessException("补货清单不能为空");
            }

            List<TaskDetails> taskDetails = detailsDTOs.stream().map(detail -> {
                TaskDetails taskDetail = new TaskDetails();
                BeanUtils.copyProperties(detail, taskDetail);
                taskDetail.setTaskId(task.getTaskId());
                return taskDetail;
            }).toList();

            taskDetailsMapper.insertTaskDetailsBatch(taskDetails);
        }
        return result;
    }

    /**
     * 检查售货机状态和工单类型是否符合逻辑
     * @param vmStatus
     * @param productTypeId
     */
    private void checkVmStatusAndTaskType(Long vmStatus, Long productTypeId){
        //1. 设备在运行中，如果是投放工单，异常
        if(vmStatus.equals(IvmConstants.VM_STATUS_RUNNING)){
            if(productTypeId.equals(IvmConstants.TASK_TYPE_DEPLOY)){
                throw new BusinessException("设备在运行中，不能进行投放工单");
            }
        }
        //2. 设备不在运行中，如果是维修，补货，撤机工单，异常
        else{
            if(productTypeId.equals(IvmConstants.TASK_TYPE_REPAIR)
                    || productTypeId.equals(IvmConstants.TASK_TYPE_SUPPLY)
                    || productTypeId.equals(IvmConstants.TASK_TYPE_REVOKE)) {
                throw new BusinessException("设备不在运行中，不能进行维修，补货，撤机工单");
            }
        }
    }

    /**
     * 判断是否存在未完成的同类工单
     * @param taskDTO
     */
    private void existsSameTypeTask(TaskDTO taskDTO){
        Task param = new Task();
        param.setInnerCode(taskDTO.getInnerCode());
        param.setProductTypeId(taskDTO.getProductTypeId());

        // 检查待处理状态
        param.setTaskStatus(IvmConstants.TASK_STATUS_CREATE);
        List<TaskVO> createList = taskMapper.selectTaskVOList(param);
        if(!CollectionUtils.isEmpty(createList)){
            throw new BusinessException("该设备存在待处理的同类工单");
        }

        // 检查进行中状态
        param.setTaskStatus(IvmConstants.TASK_STATUS_PROGRESS);
        List<TaskVO> progressList = taskMapper.selectTaskVOList(param);
        if(!CollectionUtils.isEmpty(progressList)){
            throw new BusinessException("该设备存在进行中的同类工单");
        }

    }

    /**
     * 生成工单编号
     * @return
     */
    private String generateTaskCode() {
        String dateStr= DateUtils.getDate().replaceAll("-", "");
        String key = "ivm:task:code"+dateStr;
        if(!redisTemplate.hasKey(key)){
            redisTemplate.opsForValue().set(key,1, Duration.ofDays(1));
            return dateStr+"00001";
        }

        Long sequence = redisTemplate.opsForValue().increment(key);
        return dateStr + String.format("%05d", sequence);
    }

    /**
     * 修改工单
     * 
     * @param task 工单
     * @return 结果
     */
    @Override
    public int updateTask(Task task)
    {
        task.setUpdateTime(DateUtils.getNowDate());
        return taskMapper.updateTask(task);
    }

    /**
     * 批量删除工单
     * 
     * @param taskIds 需要删除的工单主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskIds(Long[] taskIds)
    {
        return taskMapper.deleteTaskByTaskIds(taskIds);
    }

    /**
     * 删除工单信息
     * 
     * @param taskId 工单主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskId(Long taskId)
    {
        return taskMapper.deleteTaskByTaskId(taskId);
    }

    /**
     * 取消工单
     * @param task
     * @return
     */
    @Override
    public int cancelTask(Task task) {
        //1. 判断是否可以取消
        Task dbTask = taskMapper.selectTaskByTaskId(task.getTaskId());
        Long dbTaskStatus = dbTask.getTaskStatus();
        if(dbTaskStatus.equals(IvmConstants.TASK_STATUS_CANCEL)){
            throw new BusinessException("工单已取消，不能再取消");
        }
        if(dbTaskStatus.equals(IvmConstants.TASK_STATUS_FINISH)){
            throw new BusinessException("工单已完成，不能取消");
        }
        //2. 将状态设置为取消,更新时间
        task.setTaskStatus(IvmConstants.TASK_STATUS_CANCEL);
        task.setUpdateTime(DateUtils.getNowDate());
        //3. 更新工单
        return taskMapper.updateTask(task);
    }
}
