package com.stone.manage.service.impl;

import java.util.List;
import com.stone.common.utils.DateUtils;
import com.stone.framework.web.exception.BusinessException;
import com.stone.manage.mapper.VendingMachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.PolicyMapper;
import com.stone.manage.domain.Policy;
import com.stone.manage.service.IPolicyService;

/**
 * 策略管理Service业务层处理
 * 
 * @author stone
 * @date 2026-04-16
 */
@Service
public class PolicyServiceImpl implements IPolicyService 
{
    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private VendingMachineMapper vendingMachineMapper;

    /**
     * 查询策略管理
     * 
     * @param policyId 策略管理主键
     * @return 策略管理
     */
    @Override
    public Policy selectPolicyByPolicyId(Long policyId)
    {
        return policyMapper.selectPolicyByPolicyId(policyId);
    }

    /**
     * 查询策略管理列表
     * 
     * @param policy 策略管理
     * @return 策略管理
     */
    @Override
    public List<Policy> selectPolicyList(Policy policy)
    {
        return policyMapper.selectPolicyList(policy);
    }

    /**
     * 新增策略管理
     * 
     * @param policy 策略管理
     * @return 结果
     */
    @Override
    public int insertPolicy(Policy policy)
    {
        policy.setCreateTime(DateUtils.getNowDate());
        return policyMapper.insertPolicy(policy);
    }

    /**
     * 修改策略管理
     * 
     * @param policy 策略管理
     * @return 结果
     */
    @Override
    public int updatePolicy(Policy policy)
    {
        policy.setUpdateTime(DateUtils.getNowDate());
        return policyMapper.updatePolicy(policy);
    }

    /**
     * 批量删除策略管理
     * 
     * @param policyIds 需要删除的策略管理主键
     * @return 结果
     */
    @Override
    public int deletePolicyByPolicyIds(Long[] policyIds)
    {
        int count = vendingMachineMapper.countVendingMachineByPolicyIds(policyIds);
        if(count > 0){
            throw new BusinessException("存在设备信息，不允许删除");
        }
        return policyMapper.deletePolicyByPolicyIds(policyIds);
    }

    /**
     * 删除策略管理信息
     * 
     * @param policyId 策略管理主键
     * @return 结果
     */
    @Override
    public int deletePolicyByPolicyId(Long policyId)
    {
        int count = vendingMachineMapper.countVendingMachineByPolicyIds(new Long[]{policyId});
        if(count > 0){
            throw new BusinessException("策略被"+count+"个设备使用，不允许删除");
        }
        return policyMapper.deletePolicyByPolicyId(policyId);
    }
}
