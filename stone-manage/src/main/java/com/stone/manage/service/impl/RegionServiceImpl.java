package com.stone.manage.service.impl;

import java.util.List;
import com.stone.common.utils.DateUtils;
import com.stone.framework.web.exception.BusinessException;
import com.stone.manage.domain.Node;
import com.stone.manage.domain.VO.RegionVO;
import com.stone.manage.mapper.EmpMapper;
import com.stone.manage.mapper.NodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.RegionMapper;
import com.stone.manage.domain.Region;
import com.stone.manage.service.IRegionService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 区域管理Service业务层处理
 * 
 * @author stone
 * @date 2026-04-08
 */
@Service
public class RegionServiceImpl implements IRegionService 
{
    @Autowired
    private RegionMapper regionMapper;

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private EmpMapper empMapper;

    /**
     * 查询区域管理
     * 
     * @param id 区域管理主键
     * @return 区域管理
     */
    @Override
    public Region selectRegionById(Long id)
    {
        return regionMapper.selectRegionById(id);
    }

    /**
     * 查询区域管理列表
     * 
     * @param region 区域管理
     * @return 区域管理
     */
    @Override
    public List<RegionVO> selectRegionVOList(Region region)
    {
        return regionMapper.selectRegionVOList(region);
    }

    /**
     * 新增区域管理
     * 
     * @param region 区域管理
     * @return 结果
     */
    @Override
    public int insertRegion(Region region)
    {
        region.setCreateTime(DateUtils.getNowDate());
        return regionMapper.insertRegion(region);
    }

    /**
     * 修改区域管理
     * 
     * @param region 区域管理
     * @return 结果
     */
    @Transactional(rollbackFor=Exception.class)
    @Override
    public int updateRegion(Region region)
    {
        region.setUpdateTime(DateUtils.getNowDate());
        int resultRows = regionMapper.updateRegion(region);
        //同步更新员工表中的区域名称
        if(resultRows > 0){
            empMapper.updateRegionNameByRegionId(region.getRegionName(), region.getId());
        }
        return resultRows;
    }

    /**
     * 批量删除区域管理
     * 
     * @param ids 需要删除的区域管理主键
     * @return 结果
     */
    @Override
    public int deleteRegionByIds(Long[] ids)
    {
        int count = nodeMapper.countByRegionIds(ids);
        if(count>0){
            throw new BusinessException("存在点位信息，不允许删除");
        }
        return regionMapper.deleteRegionByIds(ids);
    }

    /**
     * 删除区域管理信息
     * 
     * @param id 区域管理主键
     * @return 结果
     */
    @Override
    public int deleteRegionById(Long id)
    {
        int count = nodeMapper.countByRegionIds(new Long[]{id});
        if(count>0){
            throw new BusinessException("存在点位信息，不允许删除");
        }
        return regionMapper.deleteRegionById(id);
    }
}
