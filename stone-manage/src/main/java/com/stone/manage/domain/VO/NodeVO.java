package com.stone.manage.domain.VO;

import com.stone.manage.domain.Node;
import com.stone.manage.domain.Partner;
import com.stone.manage.domain.Region;
import lombok.Data;

@Data
public class NodeVO extends Node {

    private Region region;

    private Partner partner;
    //设备数量
    private Integer vmCount;
}
