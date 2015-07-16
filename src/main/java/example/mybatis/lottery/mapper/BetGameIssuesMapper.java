package example.mybatis.lottery.mapper;

import example.mybatis.lottery.domain.BetGameIssues;

public interface BetGameIssuesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BetGameIssues record);

    int insertSelective(BetGameIssues record);

    BetGameIssues selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BetGameIssues record);

    int updateByPrimaryKey(BetGameIssues record);
}