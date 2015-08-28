package example.mybatis.youyue.mapper;

import example.mybatis.youyue.domain.CmsComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CmsComment record);

    int insertSelective(CmsComment record);

    CmsComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CmsComment record);

    int updateByPrimaryKey(CmsComment record);

    List<CmsComment> queryByCmsId(int CmsId);

    int incrementLikeCount(@Param("id") int id);
}