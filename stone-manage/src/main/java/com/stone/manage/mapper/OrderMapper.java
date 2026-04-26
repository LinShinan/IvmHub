package com.stone.manage.mapper;

import java.time.LocalDateTime;
import java.util.List;
import com.stone.manage.domain.Order;
import com.stone.manage.domain.VO.ProductSalesVO;
import com.stone.manage.domain.VO.SalesStatisticsVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 订单管理Mapper接口
 * 
 * @author stone
 * @date 2026-04-24
 */
public interface OrderMapper 
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
     * 删除订单管理
     * 
     * @param id 订单管理主键
     * @return 结果
     */
    public int deleteOrderById(Long id);

    /**
     * 批量删除订单管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);


    /**
     * 根据innerCode查询销售统计
     * @param innerCode
     * @return
     */
    public SalesStatisticsVO salesStatisticsByVmInnerCode(String innerCode);

    /**
     * 根据innerCode查询产品销售
     * @param innerCode
     * @param beginTime
     * @param endTime
     * @return
     */
    public List<ProductSalesVO> productSalesByVmInnerCode(@Param("innerCode") String innerCode, @Param("beginTime") LocalDateTime beginTime, @Param("endTime") LocalDateTime endTime);

}
