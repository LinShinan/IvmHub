package com.stone.manage.service.impl;

import java.util.List;

import com.stone.common.constant.IvmConstants;
import com.stone.common.utils.DateUtils;
import com.stone.framework.web.exception.BusinessException;
import com.stone.manage.domain.Role;
import com.stone.manage.domain.VendingMachine;
import com.stone.manage.mapper.RegionMapper;
import com.stone.manage.mapper.RoleMapper;
import com.stone.manage.mapper.VendingMachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.EmpMapper;
import com.stone.manage.domain.Emp;
import com.stone.manage.service.IEmpService;

/**
 * 人员列表Service业务层处理
 * 
 * @author stone
 * @date 2026-04-11
 */
@Service
public class EmpServiceImpl implements IEmpService 
{
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private VendingMachineMapper vendingMachineMapper;

    /**
     * 查询人员列表
     * 
     * @param id 人员列表主键
     * @return 人员列表
     */
    @Override
    public Emp selectEmpById(Long id)
    {
        return empMapper.selectEmpById(id);
    }

    /**
     * 查询人员列表列表
     * 
     * @param emp 人员列表
     * @return 人员列表
     */
    @Override
    public List<Emp> selectEmpList(Emp emp)
    {
        return empMapper.selectEmpList(emp);
    }

    /**
     * 新增人员列表
     * 
     * @param emp 人员列表
     * @return 结果
     */
    @Override
    public int insertEmp(Emp emp)
    {
        // 设置区域名称
        String regionName = regionMapper.selectRegionById(emp.getRegionId()).getRegionName();
        emp.setRegionName(regionName);
        // 设置角色代码和角色名称
        Role role = roleMapper.selectRoleByRoleId(emp.getRoleId());
        emp.setRoleCode(role.getRoleCode());
        emp.setRoleName(role.getRoleName());
        emp.setCreateTime(DateUtils.getNowDate());
        return empMapper.insertEmp(emp);
    }

    /**
     * 修改人员列表
     * 
     * @param emp 人员列表
     * @return 结果
     */
    @Override
    public int updateEmp(Emp emp)
    {
        // 设置区域名称
        String regionName = regionMapper.selectRegionById(emp.getRegionId()).getRegionName();
        emp.setRegionName(regionName);
        // 设置角色代码和角色名称
        Role role = roleMapper.selectRoleByRoleId(emp.getRoleId());
        emp.setRoleCode(role.getRoleCode());
        emp.setRoleName(role.getRoleName());
        // 设置更新时间
        emp.setUpdateTime(DateUtils.getNowDate());
        return empMapper.updateEmp(emp);
    }

    /**
     * 批量删除人员列表
     * 
     * @param ids 需要删除的人员列表主键
     * @return 结果
     */
    @Override
    public int deleteEmpByIds(Long[] ids)
    {
        return empMapper.deleteEmpByIds(ids);
    }

    /**
     * 删除人员列表信息
     * 
     * @param id 人员列表主键
     * @return 结果
     */
    @Override
    public int deleteEmpById(Long id)
    {
        return empMapper.deleteEmpById(id);
    }

    /**
     * 根据innerCode查询售货机的人员列表
     * @param innerCode
     * @return 人员列表
     */
    @Override
    public List<Emp> selectEmpsByVmAndRole(String innerCode, String roleCode) {
        //1. 根据innerCode查找售货机信息
        VendingMachine vm = vendingMachineMapper.selectVendingMachineByInnerCode(innerCode);

        //2. 根据售货机信息确定点位->区域->人员->筛选启用的xx人员
        //由于冗余字段，故可以->区域->找人员
        if(vm==null){
            throw new BusinessException("售货机不存在");
        }
        Emp emp =new Emp();
        emp.setRegionId(vm.getRegionId());
        emp.setRoleCode(roleCode);
        emp.setStatus(IvmConstants.EMP_STATUS_NORMAL);
        return empMapper.selectEmpList(emp);

    }
}
