package com.stone.manage.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.stone.common.utils.DateUtils;
import com.stone.manage.domain.DTO.ChannelConfigDTO;
import com.stone.manage.domain.DTO.ChannelSkuDTO;
import com.stone.manage.domain.VO.ChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stone.manage.mapper.ChannelMapper;
import com.stone.manage.domain.Channel;
import com.stone.manage.service.IChannelService;

/**
 * 售货机货道Service业务层处理
 * 
 * @author stone
 * @date 2026-04-12
 */
@Service
public class ChannelServiceImpl implements IChannelService 
{
    @Autowired
    private ChannelMapper channelMapper;

    /**
     * 查询售货机货道
     * 
     * @param id 售货机货道主键
     * @return 售货机货道
     */
    @Override
    public Channel selectChannelById(Long id)
    {
        return channelMapper.selectChannelById(id);
    }

    /**
     * 查询售货机货道列表
     * 
     * @param channel 售货机货道
     * @return 售货机货道
     */
    @Override
    public List<Channel> selectChannelList(Channel channel)
    {
        return channelMapper.selectChannelList(channel);
    }

    /**
     * 新增售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    @Override
    public int insertChannel(Channel channel)
    {
        channel.setCreateTime(DateUtils.getNowDate());
        return channelMapper.insertChannel(channel);
    }

    /**
     * 修改售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    @Override
    public int updateChannel(Channel channel)
    {
        channel.setUpdateTime(DateUtils.getNowDate());
        return channelMapper.updateChannel(channel);
    }

    /**
     * 批量删除售货机货道
     * 
     * @param ids 需要删除的售货机货道主键
     * @return 结果
     */
    @Override
    public int deleteChannelByIds(Long[] ids)
    {
        return channelMapper.deleteChannelByIds(ids);
    }

    /**
     * 删除售货机货道信息
     * 
     * @param id 售货机货道主键
     * @return 结果
     */
    @Override
    public int deleteChannelById(Long id)
    {
        return channelMapper.deleteChannelById(id);
    }

    /**
     * 根据内部编码查询售货机货道
     *
     * @param innerCode 内部编码
     * @return 售货机货道
     */
    @Override
    public List<ChannelVO> listChannelByInnerCode(String innerCode) {
        return channelMapper.listChannelByInnerCode(innerCode);
    }

    /**
     * 设置货道信息
     *
     * @param channelConfigDTO 货道信息
     * @return 售货机货道
     */
    @Override
    public int setChannel(ChannelConfigDTO channelConfigDTO){
        //1.查询该售货机的所有货道
        List<ChannelVO> channels = channelMapper.listChannelByInnerCode(channelConfigDTO.getInnerCode());

        //2.设置map建立索引，将channelCode和Channel应起来，快速查询
        Map<String,Channel> channelMap = channels.stream()
                .collect(Collectors.toMap(Channel::getChannelCode, channel -> channel));

        //3. 根据map批量设置货道信息
        List<ChannelSkuDTO> channelList = channelConfigDTO.getChannelList();
        List<Channel> updateList = channelList.stream().map(dto ->{
            Channel channel = channelMap.get(dto.getChannelCode());
            if(channel!=null){
                channel.setSkuId(dto.getSkuId());
                channel.setUpdateTime(DateUtils.getNowDate());
            }
            return channel;
        }).filter(channel -> channel != null).toList();
        //4. 批量更新货道信息
        return channelMapper.updateChannelBatch(updateList);
    }
}
