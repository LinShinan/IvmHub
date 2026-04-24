package com.stone.manage.mapper;

import java.util.List;
import com.stone.manage.domain.VendingMachine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 设备管理Mapper接口
 * 
 * @author stone
 * @date 2026-04-12
 */
public interface VendingMachineMapper 
{
    /**
     * 查询设备管理
     * 
     * @param id 设备管理主键
     * @return 设备管理
     */
    public VendingMachine selectVendingMachineById(Long id);

    /**
     * 查询设备管理列表
     * 
     * @param vendingMachine 设备管理
     * @return 设备管理集合
     */
    public List<VendingMachine> selectVendingMachineList(VendingMachine vendingMachine);

    /**
     * 新增设备管理
     * 
     * @param vendingMachine 设备管理
     * @return 结果
     */
    public int insertVendingMachine(VendingMachine vendingMachine);

    /**
     * 修改设备管理
     * 
     * @param vendingMachine 设备管理
     * @return 结果
     */
    public int updateVendingMachine(VendingMachine vendingMachine);

    /**
     * 删除设备管理
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    public int deleteVendingMachineById(Long id);

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVendingMachineByIds(Long[] ids);

    /**
     * 根据设备类型ID集合统计设备管理数量
     *
     * @param vmTypeIds 设备类型ID集合
     * @return 结果
     */
    public int countVendingMachineByVmTypeIds(@Param("vmTypeIds") Long[] vmTypeIds);

    /**
     * 根据策略ID集合统计设备管理数量
     *
     * @param policyIds 策略ID集合
     * @return 结果
     */
    public int countVendingMachineByPolicyIds(@Param("policyIds") Long[] policyIds);

    /**
     * 根据innerCode查询设备
     * @param innerCode
     * @return
     */
    @Select("select * from tb_vending_machine where inner_code = #{innerCode}")
    public VendingMachine selectVendingMachineByInnerCode(String innerCode);

}
