package example.mybatis.youyue.mapper;

import example.mybatis.youyue.domain.Cms;

public interface CmsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cms record);

    int insertSelective(Cms record);

    Cms selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cms record);

    int updateByPrimaryKey(Cms record);
}