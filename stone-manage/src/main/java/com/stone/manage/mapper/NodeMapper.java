package com.stone.manage.mapper;

import java.util.List;
import com.stone.manage.domain.Node;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 点位管理Mapper接口
 * 
 * @author stone
 * @date 2026-04-08
 */
public interface NodeMapper 
{
    /**
     * 查询点位管理
     * 
     * @param id 点位管理主键
     * @return 点位管理
     */
    Node selectNodeById(Long id);

    /**
     * 查询点位管理列表
     * 
     * @param node 点位管理
     * @return 点位管理集合
     */
    List<Node> selectNodeVOList(Node node);

    /**
     * 新增点位管理
     * 
     * @param node 点位管理
     * @return 结果
     */
    int insertNode(Node node);

    /**
     * 修改点位管理
     * 
     * @param node 点位管理
     * @return 结果
     */
    int updateNode(Node node);

    /**
     * 删除点位管理
     * 
     * @param id 点位管理主键
     * @return 结果
     */
    int deleteNodeById(Long id);

    /**
     * 批量删除点位管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteNodeByIds(Long[] ids);

    /**
     * 根据区域ID统计点位数量
     *
     * @param regionIds 区域ID数组
     * @return 点位数量
     */
    int countByRegionIds(@Param("regionIds") Long[] regionIds);

    /**
     * 根据合作商ID统计点位数量
     * @param partnerIds
     * @return
     */
    int countByPartnerIds(@Param("partnerIds") Long[] partnerIds);
}
