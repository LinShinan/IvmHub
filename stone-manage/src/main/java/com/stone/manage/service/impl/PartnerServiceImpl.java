package com.stone.manage.service.impl;

import java.util.List;
import com.stone.common.utils.DateUtils;
import com.stone.common.utils.SecurityUtils;
import com.stone.manage.domain.VO.PartnerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.PartnerMapper;
import com.stone.manage.domain.Partner;
import com.stone.manage.service.IPartnerService;

/**
 * 合作商管理Service业务层处理
 * 
 * @author stone
 * @date 2026-04-08
 */
@Service
public class PartnerServiceImpl implements IPartnerService 
{
    @Autowired
    private PartnerMapper partnerMapper;

    /**
     * 查询合作商管理
     * 
     * @param id 合作商管理主键
     * @return 合作商管理
     */
    @Override
    public Partner selectPartnerById(Long id)
    {
        Partner partner = partnerMapper.selectPartnerById(id);
        if(partner!=null){
            partner.setPassword("******");
        }
        return partner;
    }

    /**
     * 查询合作商管理列表
     * 
     * @param partner 合作商管理
     * @return 合作商管理
     */
    @Override
    public List<PartnerVO> selectPartnerVOList(Partner partner)
    {
        List<PartnerVO> partnerVO = partnerMapper.selectPartnerVOList(partner);
        for(PartnerVO p: partnerVO){
            p.setPassword("******");
        }
        return partnerVO;
    }

    /**
     * 新增合作商管理
     * 
     * @param partner 合作商管理
     * @return 结果
     */
    @Override
    public int insertPartner(Partner partner)
    {
        // 密码加密
        partner.setPassword(SecurityUtils.encryptPassword(partner.getPassword()));
        partner.setCreateTime(DateUtils.getNowDate());
        return partnerMapper.insertPartner(partner);
    }

    /**
     * 修改合作商管理
     * 
     * @param partner 合作商管理
     * @return 结果
     */
    @Override
    public int updatePartner(Partner partner)
    {
        partner.setUpdateTime(DateUtils.getNowDate());
        return partnerMapper.updatePartner(partner);
    }

    /**
     * 批量删除合作商管理
     * 
     * @param ids 需要删除的合作商管理主键
     * @return 结果
     */
    @Override
    public int deletePartnerByIds(Long[] ids)
    {
        return partnerMapper.deletePartnerByIds(ids);
    }

    /**
     * 删除合作商管理信息
     * 
     * @param id 合作商管理主键
     * @return 结果
     */
    @Override
    public int deletePartnerById(Long id)
    {
        return partnerMapper.deletePartnerById(id);
    }
}
