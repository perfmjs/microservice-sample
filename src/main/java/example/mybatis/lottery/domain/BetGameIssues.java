package example.mybatis.lottery.domain;

import com.github.dbutils.mybatis.extend.AbstractMyBatisEntity;
import example.mybatis.lottery.enums.Game;
import example.mybatis.lottery.enums.IssueStatus;

import java.util.Calendar;

public class BetGameIssues extends AbstractMyBatisEntity {
    private Integer id;

    private String issueNo;

    private Game gameId;

    private Boolean isCurrent;

    private Calendar startTime;

    private Calendar endTime;

    private Calendar openTime;

    private IssueStatus status;

    private Integer preIssue;

    private String result;

    private Integer prizePool;

    private Long bitFlag;

    private String extendFeatures;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public Game getGameId() {
        return gameId;
    }

    public void setGameId(Game gameId) {
        this.gameId = gameId;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Calendar getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Calendar openTime) {
        this.openTime = openTime;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public Integer getPreIssue() {
        return preIssue;
    }

    public void setPreIssue(Integer preIssue) {
        this.preIssue = preIssue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(Integer prizePool) {
        this.prizePool = prizePool;
    }

}