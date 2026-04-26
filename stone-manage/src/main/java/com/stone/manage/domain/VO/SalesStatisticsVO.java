package com.stone.manage.domain.VO;

import lombok.Data;

@Data
public class SalesStatisticsVO {
    /** 销售量（订单数） */
    private Integer salesVolume;

    /** 销售额（总金额） */
    private Integer salesAmount;
}
