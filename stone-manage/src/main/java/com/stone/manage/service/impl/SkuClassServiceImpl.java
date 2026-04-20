package com.stone.manage.service.impl;

import java.util.List;

import com.stone.framework.web.exception.BusinessException;
import com.stone.manage.mapper.SkuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.SkuClassMapper;
import com.stone.manage.domain.SkuClass;
import com.stone.manage.service.ISkuClassService;

/**
 * 商品类型Service业务层处理
 * 
 * @author stone
 * @date 2026-04-17
 */
@Service
public class SkuClassServiceImpl implements ISkuClassService 
{
    @Autowired
    private SkuClassMapper skuClassMapper;

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 查询商品类型
     * 
     * @param classId 商品类型主键
     * @return 商品类型
     */
    @Override
    public SkuClass selectSkuClassByClassId(Long classId)
    {
        return skuClassMapper.selectSkuClassByClassId(classId);
    }

    /**
     * 查询商品类型列表
     * 
     * @param skuClass 商品类型
     * @return 商品类型
     */
    @Override
    public List<SkuClass> selectSkuClassList(SkuClass skuClass)
    {
        return skuClassMapper.selectSkuClassList(skuClass);
    }

    /**
     * 新增商品类型
     * 
     * @param skuClass 商品类型
     * @return 结果
     */
    @Override
    public int insertSkuClass(SkuClass skuClass)
    {
        return skuClassMapper.insertSkuClass(skuClass);
    }

    /**
     * 修改商品类型
     * 
     * @param skuClass 商品类型
     * @return 结果
     */
    @Override
    public int updateSkuClass(SkuClass skuClass)
    {
        return skuClassMapper.updateSkuClass(skuClass);
    }

    /**
     * 批量删除商品类型
     * 
     * @param classIds 需要删除的商品类型主键
     * @return 结果
     */
    @Override
    public int deleteSkuClassByClassIds(Long[] classIds)
    {
        int count = skuMapper.countByClassIds(classIds);
        if(count > 0){
            throw new BusinessException("商品类型存在关联商品，不允许删除");
        }
        return skuClassMapper.deleteSkuClassByClassIds(classIds);
    }

    /**
     * 删除商品类型信息
     * 
     * @param classId 商品类型主键git
     * @return 结果
     */
    @Override
    public int deleteSkuClassByClassId(Long classId)
    {
        int count = skuMapper.countByClassIds(new Long[]{classId});
        if(count > 0){
            throw new BusinessException("商品分类下有"+count+"个商品，不允许删除");
        }
        return skuClassMapper.deleteSkuClassByClassId(classId);
    }
}
