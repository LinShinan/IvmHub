package com.stone.manage.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.stone.common.constant.IvmConstants;
import com.stone.common.utils.DateUtils;
import com.stone.common.utils.uuid.UUIDUtils;
import com.stone.manage.domain.Channel;
import com.stone.manage.domain.Node;
import com.stone.manage.domain.VmType;
import com.stone.manage.mapper.ChannelMapper;
import com.stone.manage.mapper.NodeMapper;
import com.stone.manage.mapper.VmTypeMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.VendingMachineMapper;
import com.stone.manage.domain.VendingMachine;
import com.stone.manage.service.IVendingMachineService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 设备管理Service业务层处理
 * 
 * @author stone
 * @date 2026-04-12
 */
@Service
public class VendingMachineServiceImpl implements IVendingMachineService 
{
    @Autowired
    private VendingMachineMapper vendingMachineMapper;

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private VmTypeMapper vmTypeMapper;

    @Autowired
    private NodeMapper nodeMapper;

    /**
     * 查询设备管理
     * 
     * @param id 设备管理主键
     * @return 设备管理
     */
    @Override
    public VendingMachine selectVendingMachineById(Long id)
    {
        return vendingMachineMapper.selectVendingMachineById(id);
    }

    /**
     * 查询设备管理列表
     * 
     * @param vendingMachine 设备管理
     * @return 设备管理
     */
    @Override
    public List<VendingMachine> selectVendingMachineList(VendingMachine vendingMachine)
    {
        return vendingMachineMapper.selectVendingMachineList(vendingMachine);
    }

    /**
     * 新增设备管理
     * 
     * @param vendingMachine 设备管理
     * @return 结果
     */
    //事务
    @Transactional(rollbackFor =Exception.class)
    @Override
    public int insertVendingMachine(VendingMachine vendingMachine)
    {
        VmType vmType = vmTypeMapper.selectVmTypeById(vendingMachine.getVmTypeId());
        Long channelMaxCapacity = vmType.getChannelMaxCapacity();

        Node node = nodeMapper.selectNodeById(vendingMachine.getNodeId());
        //新增设备
        BeanUtils.copyProperties(node,vendingMachine,"id");

        vendingMachine.setInnerCode(UUIDUtils.getUUID());
        vendingMachine.setChannelMaxCapacity(channelMaxCapacity);
        vendingMachine.setAddr(node.getAddress()); //因为node的字段是address,和vendingMachine的addr不同
        vendingMachine.setVmStatus(IvmConstants.VM_STATUS_NODEPLOY);
        vendingMachine.setUpdateTime(DateUtils.getNowDate());
        vendingMachine.setCreateTime(DateUtils.getNowDate());


        int res = vendingMachineMapper.insertVendingMachine(vendingMachine);


        //新增货道
        List<Channel> channelList = new ArrayList<>();
        for(int i=1;i<=vmType.getVmRow();i++){
            for(int j=1;j<=vmType.getVmCol();j++){
                Channel channel = new Channel();
                channel.setChannelCode(i+"-"+j);
                channel.setInnerCode(vendingMachine.getInnerCode());
                channel.setVmId(vendingMachine.getId());
                channel.setMaxCapacity(channelMaxCapacity);
                channel.setCreateTime(DateUtils.getNowDate());
                channel.setUpdateTime(DateUtils.getNowDate());
                channelList.add(channel);
            }
        }
        if(!channelList.isEmpty()){
            channelMapper.insertChannelBatch(channelList);
        }

        return res;
    }

    /**
     * 修改设备管理
     * 
     * @param vendingMachine 设备管理
     * @return 结果
     */
    @Override
    public int updateVendingMachine(VendingMachine vendingMachine)
    {
        Long nodeId = vendingMachine.getNodeId();
        if(nodeId != null){
            Node node = nodeMapper.selectNodeById(nodeId);
            BeanUtils.copyProperties(node,vendingMachine,"id");
            vendingMachine.setAddr(node.getAddress());
        }
        vendingMachine.setUpdateTime(DateUtils.getNowDate());
        return vendingMachineMapper.updateVendingMachine(vendingMachine);
    }

    /**
     * 批量删除设备管理
     * 
     * @param ids 需要删除的设备管理主键
     * @return 结果
     */
    @Override
    public int deleteVendingMachineByIds(Long[] ids)
    {
        return vendingMachineMapper.deleteVendingMachineByIds(ids);
    }

    /**
     * 删除设备管理信息
     * 
     * @param id 设备管理主键
     * @return 结果
     */
    @Override
    public int deleteVendingMachineById(Long id)
    {
        return vendingMachineMapper.deleteVendingMachineById(id);
    }
}
