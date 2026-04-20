package com.stone.manage.domain.DTO;

import lombok.Data;

import java.util.List;

//ChannelSkuDTO可以确认具体货道，
//ChannelConfigDTO是确认哪个机器的货道，利用ChannelSkuDTO批量设置机器的所有货道
@Data
public class ChannelConfigDTO {

    private String innerCode;

    private List<ChannelSkuDTO> channelList;
}
