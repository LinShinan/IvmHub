package com.stone.manage.domain.DTO;

import lombok.Data;

@Data
public class TaskDetailsDTO {

    /** 货道编号 */
    private String channelCode;

    /** 补货容量 */
    private Long expectCapacity;

    /** 商品ID */
    private Long skuId;

    /** 商品名称 */
    private String skuName;

    /** 商品图片 */
    private String skuImage;
}
