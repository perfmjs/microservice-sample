package example.mybatis.youyue.mapper;

import example.mybatis.youyue.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByNameAndPasswd(@Param("name")String name, @Param("passwd")String passwd);
}