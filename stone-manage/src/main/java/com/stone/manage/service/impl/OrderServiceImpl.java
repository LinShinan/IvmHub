package com.stone.manage.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.stone.common.utils.DateUtils;
import com.stone.manage.domain.VO.ProductSalesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.OrderMapper;
import com.stone.manage.domain.Order;
import com.stone.manage.service.IOrderService;

/**
 * 订单管理Service业务层处理
 * 
 * @author stone
 * @date 2026-04-24
 */
@Service
public class OrderServiceImpl implements IOrderService 
{
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询订单管理
     * 
     * @param id 订单管理主键
     * @return 订单管理
     */
    @Override
    public Order selectOrderById(Long id)
    {

        return orderMapper.selectOrderById(id);
    }

    /**
     * 查询订单管理列表
     * 
     * @param order 订单管理
     * @return 订单管理
     */
    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    @Override
    public int insertOrder(Order order)
    {
        order.setCreateTime(DateUtils.getNowDate());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单管理
     * 
     * @param order 订单管理
     * @return 结果
     */
    @Override
    public int updateOrder(Order order)
    {
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(Long[] ids)
    {
        return orderMapper.deleteOrderByIds(ids);
    }

    /**
     * 删除订单管理信息
     * 
     * @param id 订单管理主键
     * @return 结果
     */
    @Override
    public int deleteOrderById(Long id)
    {
        return orderMapper.deleteOrderById(id);
    }

    /**
     * 获取某售货机最近一个月的商品销售数据
     * @param innerCode
     * @return
     */
    @Override
    public List<ProductSalesVO> selectProductSalesListByMonth(String innerCode) {
        LocalDate nowDate = LocalDate.now();
        LocalDateTime beginTime = nowDate.withDayOfMonth(1).atTime(0,0,0);
        LocalDateTime endTime =nowDate.withDayOfMonth(nowDate.lengthOfMonth()).atTime(23,59,59,999999999);

        System.out.println("beginTime:"+beginTime+" endTime:"+endTime);
        return orderMapper.productSalesByVmInnerCode(innerCode,beginTime,endTime);
    }

}
