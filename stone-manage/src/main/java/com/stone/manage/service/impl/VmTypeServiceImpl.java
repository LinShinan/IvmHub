package com.stone.manage.service.impl;

import java.util.List;

import com.stone.framework.web.exception.BusinessException;
import com.stone.manage.mapper.VendingMachineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.VmTypeMapper;
import com.stone.manage.domain.VmType;
import com.stone.manage.service.IVmTypeService;

/**
 * 设备类型管理Service业务层处理
 * 
 * @author stone
 * @date 2026-04-12
 */
@Service
public class VmTypeServiceImpl implements IVmTypeService 
{
    @Autowired
    private VmTypeMapper vmTypeMapper;

    @Autowired
    private VendingMachineMapper vendingMachineMapper;
    /**
     * 查询设备类型管理
     * 
     * @param id 设备类型管理主键
     * @return 设备类型管理
     */
    @Override
    public VmType selectVmTypeById(Long id)
    {
        return vmTypeMapper.selectVmTypeById(id);
    }

    /**
     * 查询设备类型管理列表
     * 
     * @param vmType 设备类型管理
     * @return 设备类型管理
     */
    @Override
    public List<VmType> selectVmTypeList(VmType vmType)
    {
        return vmTypeMapper.selectVmTypeList(vmType);
    }

    /**
     * 新增设备类型管理
     * 
     * @param vmType 设备类型管理
     * @return 结果
     */
    @Override
    public int insertVmType(VmType vmType)
    {
        return vmTypeMapper.insertVmType(vmType);
    }

    /**
     * 修改设备类型管理
     * 
     * @param vmType 设备类型管理
     * @return 结果
     */
    @Override
    public int updateVmType(VmType vmType)
    {
        return vmTypeMapper.updateVmType(vmType);
    }

    /**
     * 批量删除设备类型管理
     * 
     * @param ids 需要删除的设备类型管理主键
     * @return 结果
     */
    @Override
    public int deleteVmTypeByIds(Long[] ids)
    {
        int count = vendingMachineMapper.countVendingMachineByVmTypeIds(ids);
        if(count > 0){
            throw new BusinessException("存在设备信息，不允许删除");
        }
        return vmTypeMapper.deleteVmTypeByIds(ids);
    }

    /**
     * 删除设备类型管理信息
     * 
     * @param id 设备类型管理主键
     * @return 结果
     */
    @Override
    public int deleteVmTypeById(Long id)
    {
        int count = vendingMachineMapper.countVendingMachineByVmTypeIds(new Long[]{id});
        if(count > 0){
            throw new BusinessException("设备类型被"+count+"个设备使用，不允许删除");
        }
        return vmTypeMapper.deleteVmTypeById(id);
    }
}
