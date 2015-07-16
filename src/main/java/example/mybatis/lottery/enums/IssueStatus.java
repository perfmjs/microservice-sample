package example.mybatis.lottery.enums;


import com.github.dbutils.mybatis.enums.IEnum;

/**
 * 奖期状态
 * @author tony.shen
 */
public enum IssueStatus implements IEnum {

    PRESELLING(0, "预售中"),
    SALEING(1, "销售中"),
    TEMPSTOP(2, "暂停销售"),
    GROUPSTOP(3, "合买截止"),
    SALESTOP(4, "销售停止"),
    WAITDRAW(5, "等待开奖"), //过了开奖时间未拿到开奖号码
    OPENPRIZED(6, "已开奖"),
    FINISHPRIZED(7, "已兑奖");

    private int index;
    private String label;
    private IssueStatus(int index, String label) {
        this.index = index;
        this.label = label;
    }
    @Override
    public int getIndex() {
        return this.index;
    }
    @Override
    public String getLabel() {
        return this.label;
    }

}