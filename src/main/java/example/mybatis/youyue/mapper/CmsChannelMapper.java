package example.mybatis.youyue.mapper;

import example.mybatis.youyue.domain.CmsChannel;

import java.util.List;
import java.util.Map;

public interface CmsChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsChannel record);

    int insertSelective(CmsChannel record);

    CmsChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsChannel record);

    int updateByPrimaryKey(CmsChannel record);

    List<CmsChannel> queryAll();
}