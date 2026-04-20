package com.stone.manage.service.impl;

import java.util.List;
import com.stone.common.utils.DateUtils;
import com.stone.framework.web.exception.BusinessException;
import com.stone.manage.domain.VO.SkuVO;
import com.stone.manage.mapper.ChannelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.SkuMapper;
import com.stone.manage.domain.Sku;
import com.stone.manage.service.ISkuService;

/**
 * 商品管理Service业务层处理
 * 
 * @author stone
 * @date 2026-04-17
 */
@Service
public class SkuServiceImpl implements ISkuService 
{
    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private ChannelMapper channelMapper;

    /**
     * 查询商品管理
     * 
     * @param skuId 商品管理主键
     * @return 商品管理
     */
    @Override
    public Sku selectSkuBySkuId(Long skuId)
    {
        return skuMapper.selectSkuBySkuId(skuId);
    }

    /**
     * 查询商品管理列表
     * 
     * @param sku 商品管理
     * @return 商品管理
     */
    @Override
    public List<SkuVO> selectSkuVOList(Sku sku)
    {
        return skuMapper.selectSkuVOList(sku);
    }

    /**
     * 新增商品管理
     * 
     * @param sku 商品管理
     * @return 结果
     */
    @Override
    public int insertSku(Sku sku)
    {
        sku.setCreateTime(DateUtils.getNowDate());
        return skuMapper.insertSku(sku);
    }

    /**
     * 修改商品管理
     * 
     * @param sku 商品管理
     * @return 结果
     */
    @Override
    public int updateSku(Sku sku)
    {
        sku.setUpdateTime(DateUtils.getNowDate());
        return skuMapper.updateSku(sku);
    }

    /**
     * 批量删除商品管理
     * 
     * @param skuIds 需要删除的商品管理主键
     * @return 结果
     */
    @Override
    public int deleteSkuBySkuIds(Long[] skuIds)
    {
        int count= channelMapper.countChannelBySkuIds(skuIds);
        if(count > 0){
            throw new BusinessException("商品存在关联货道，不允许删除");
        }
        return skuMapper.deleteSkuBySkuIds(skuIds);
    }

    /**
     * 删除商品管理信息
     * 
     * @param skuId 商品管理主键
     * @return 结果
     */
    @Override
    public int deleteSkuBySkuId(Long skuId)
    {
        int count= channelMapper.countChannelBySkuIds(new Long[]{skuId});
        if(count > 0){
            throw new BusinessException("改商品在"+count+"个货道中存在，不允许删除");
        }
        return skuMapper.deleteSkuBySkuId(skuId);
    }

    /**
     * 批量新增商品管理
     * @param skus
     * @return
     */
    @Override
    public int insertSkuBatch(List<Sku> skus) {
        int result = skuMapper.insertSkuBatch(skus);
        return result;
    }
}
