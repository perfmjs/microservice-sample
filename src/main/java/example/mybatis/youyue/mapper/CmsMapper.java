package example.mybatis.youyue.mapper;

import example.mybatis.youyue.domain.Cms;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cms record);

    int insertSelective(Cms record);

    Cms selectByPrimaryKey(Integer id);

    List<Cms> queryByChannel(@Param("channelName") String channel);

    int updateByPrimaryKeySelective(Cms record);

    int updateByPrimaryKey(Cms record);

    int incrementLikeCount(@Param("cmsId") int cmsId);

    int incrementUnlikeCount(@Param("cmsId") int cmsId);
}