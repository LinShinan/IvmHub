package com.stone.manage.domain.VO;

import com.stone.manage.domain.Channel;
import com.stone.manage.domain.Sku;
import lombok.Data;

@Data
public class ChannelVO extends Channel {
    // 商品
    private Sku sku;
}
