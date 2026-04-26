package com.stone.manage.domain.VO;

import lombok.Data;

import java.util.List;

@Data
public class VmDetailsVO {
    /** 销售量（订单数） */
    private Integer salesVolume;

    /** 销售额（总金额） */
    private Integer salesAmount;

    /** 补货次数 */
    private Integer supplyCount;

    /** 维修次数 */
    private Integer repairCount;

    /** 商品销量列表 */
    private List<ProductSalesVO> productSales;
}
