package com.stone.manage.domain.VO;

import lombok.Data;

@Data
public class ProductSalesVO {

    /** 商品ID */
    private Long skuId;

    /** 商品名称 */
    private String skuName;

    /** 销量 */
    private Integer total;
}
