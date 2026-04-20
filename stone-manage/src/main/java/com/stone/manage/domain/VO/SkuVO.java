package com.stone.manage.domain.VO;

import com.stone.manage.domain.Sku;

public class SkuVO extends Sku{

    private String skuClassName;//商品类型名称

    public String getSkuClassName() {
        return skuClassName;
    }

    public void setSkuClassName(String skuClassName) {
        this.skuClassName = skuClassName;
    }
}
