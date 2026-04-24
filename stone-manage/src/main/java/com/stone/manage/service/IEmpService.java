package com.stone.manage.service;

import java.util.List;
import com.stone.manage.domain.Emp;

/**
 * 人员列表Service接口
 * 
 * @author stone
 * @date 2026-04-11
 */
public interface IEmpService 
{
    /**
     * 查询人员列表
     * 
     * @param id 人员列表主键
     * @return 人员列表
     */
    public Emp selectEmpById(Long id);

    /**
     * 查询人员列表列表
     * 
     * @param emp 人员列表
     * @return 人员列表集合
     */
    public List<Emp> selectEmpList(Emp emp);

    /**
     * 新增人员列表
     * 
     * @param emp 人员列表
     * @return 结果
     */
    public int insertEmp(Emp emp);

    /**
     * 修改人员列表
     * 
     * @param emp 人员列表
     * @return 结果
     */
    public int updateEmp(Emp emp);

    /**
     * 批量删除人员列表
     * 
     * @param ids 需要删除的人员列表主键集合
     * @return 结果
     */
    public int deleteEmpByIds(Long[] ids);

    /**
     * 删除人员列表信息
     * 
     * @param id 人员列表主键
     * @return 结果
     */
    public int deleteEmpById(Long id);


    /**
     * 根据售货机和角色查询人员列表
     *
     * @param innerCode 虚拟机内码
     * @param roleCode 角色代码
     * @return 人员列表
     */
    List<Emp> selectEmpsByVmAndRole(String innerCode, String roleCode);
}
