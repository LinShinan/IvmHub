package com.stone.manage.service;

import java.util.List;
import com.stone.manage.domain.Order;
import com.stone.manage.domain.VO.ProductSalesVO;

/**
 * 订单管理Service接口
 * 
 * @author stone
 * @date 2026-04-24
 */
public interface IOrderService 
{
    /**
     * 查询订单管理
     * 
     * @param id 订单管理主键
     * @return 订单管理
     */
    public Order selectOrderById(Long id);

    /**
     * 查询订单管理列表
     * 
     * @param order 订单管理
     * @return 订单管理集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的订单管理主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);

    /**
     * 删除订单管理信息
     * 
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteOrderById(Long id);

    /**
     * 获取某售货机最近一个月的商品销售数据
     * @param innerCode
     * @return
     */
    public List<ProductSalesVO> selectProductSalesListByMonth(String innerCode);
}
