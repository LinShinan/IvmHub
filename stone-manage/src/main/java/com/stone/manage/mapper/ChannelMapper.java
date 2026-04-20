package com.stone.manage.mapper;

import java.util.List;
import com.stone.manage.domain.Channel;
import com.stone.manage.domain.VO.ChannelVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 售货机货道Mapper接口
 * 
 * @author stone
 * @date 2026-04-12
 */
public interface ChannelMapper 
{
    /**
     * 查询售货机货道
     * 
     * @param id 售货机货道主键
     * @return 售货机货道
     */
    public Channel selectChannelById(Long id);

    /**
     * 查询售货机货道列表
     * 
     * @param channel 售货机货道
     * @return 售货机货道集合
     */
    public List<Channel> selectChannelList(Channel channel);

    /**
     * 新增售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    public int insertChannel(Channel channel);

    /**
     * 批量新增售货机货道
     * @param channelList
     * @return
     */
    public int insertChannelBatch(@Param("channelList") List<Channel> channelList);

    /**
     * 修改售货机货道
     * 
     * @param channel 售货机货道
     * @return 结果
     */
    public int updateChannel(Channel channel);

    /**
     * 删除售货机货道
     * 
     * @param id 售货机货道主键
     * @return 结果
     */
    public int deleteChannelById(Long id);

    /**
     * 批量删除售货机货道
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChannelByIds(Long[] ids);


    /**
     * 根据skuIds查询售货机货道数量
     *
     * @param skuIds skuIds
     * @return 结果
     */
    public int countChannelBySkuIds(@Param("skuIds") Long[] skuIds);

    /**
     * 根据innerCode查询售货机货道
     *
     * @param innerCode innerCode
     * @return 售货机货道
     */
    List<ChannelVO> listChannelByInnerCode(String innerCode);


    int updateChannelBatch(@Param("channelList") List<Channel> channelList);
}
