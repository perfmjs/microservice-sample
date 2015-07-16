package example.mybatis.lottery.enums;

import com.github.dbutils.mybatis.enums.IEnum;


/**
 * 彩种:福彩(1x),体彩(2x),快开彩(3x),竟技彩(5x)
 * @author tony.shen
 *
 */
public enum Game implements IEnum {

    /**福彩-双色球 */
    FC_SSQ(101, "双色球", 7, null),
    /**福彩-3D */
    FC_3D(102, "福彩3D", 3, null),
    /**福彩-15选5 */
    FC_15x5(103, "15选5", 5, null),
    /**福彩-七乐彩 */
    FC_QLC(104, "七乐彩", 8, null),
    /**福彩- */
    FC_DF6j1(105, "东方6+1", 7, null),
    /**福彩- */
    FC_HC1(106, "好彩1", 4, null),

    /**体彩-大乐透*/
    TC_DLT(201, "大乐透", 7, null),

    /**体彩-排列五*/
    TC_PL5(203, "排列五", 5, null),
    /**体彩-排列三*/
    TC_PL3(202, "排列三", 3, TC_PL5),
    /**体彩-七星彩*/
    TC_QXC(204, "七星彩", 7, null),

    /**快开-重庆时时彩*/
    KC_CQSSC(301, "重庆时时彩", 5, null),
    /**快开-时时乐*/
    KC_SSL(302, "时时乐", 3, null),

    /**快开-十一运夺金(山东11选5)*/
    KC_11YDJ(303, "十一运夺金", 5, null),
    /**快开-江西多乐彩(江西11选5)*/
    KC_DLC(304, "江西多乐彩", 5, null),
    /**快开-广东11选5 */
    KC_GD11x5(305, "广东11选5", 5, null),

    /**竟技-14场胜负彩*/
    JJ_14SFC(401, "14场胜负彩", 14, null),
    /**竟技-任选9场*/
    JJ_R9C(402, "任选9场", 14, JJ_14SFC),
    /**竟技-4场进球彩*/
    JJ_4CJQC(403, "4场进球彩", 8, null),
    /**竟技-6场半全场*/
    JJ_6CBQC(404, "6场半全场", 12, null),

    /**竟技-北京单场*/
    JJ_BJDC(405, "北京单场", 0, null),
    /**竟技-北京单场-胜平负*/
    JJ_BJDC_SPF(4051, "胜平负", 0, JJ_BJDC),
    /**竟技-北京单场-上下单双*/
    JJ_BJDC_SXDS(4052, "上下单双", 0, JJ_BJDC),
    /**竟技-北京单场-总进球*/
    JJ_BJDC_ZJQ(4053, "总进球", 0, JJ_BJDC),
    /**竟技-北京单场-比分*/
    JJ_BJDC_BF(4054, "比分", 0, JJ_BJDC),
    /**竟技-北京单场-半场胜平负*/
    JJ_BJDC_BCSPF(4055, "半场胜平负", 0, JJ_BJDC),

    /**竟技-篮彩*/
    JJ_LC(406, "篮彩", 0, null),
    /**竟技-篮彩-胜负*/
    JJ_LC_SF(4061, "胜负", 0, JJ_LC),
    /**竟技-篮彩-让分胜分*/
    JJ_LC_RFSF(4062, "让分胜分", 0, JJ_LC),
    /**竟技-篮彩-胜分差*/
    JJ_LC_SFC(4063, "胜分差", 0, JJ_LC),
    /**竟技-篮彩-大小分*/
    JJ_LC_DXF(4064, "大小分", 0, JJ_LC),

    /**竟彩足球*/
    JJ_ZC(407, "竟彩足球", 0, null),
    /**竟彩足球-胜平负*/
    JJ_ZC_SPF(4071, "胜平负", 0, JJ_ZC),
    /**竟彩足球-总进球数*/
    JJ_ZC_ZJQ(4072, "总进球数", 0, JJ_ZC),
    /**竟彩足球-全场比分*/
    JJ_ZC_BF(4073, "全场比分", 0, JJ_ZC),
    /**竟彩足球-半全场胜平负*/
    JJ_ZC_BCSPF(4074, "半全场胜平负", 0, JJ_ZC);

    /** 顺序 */
    private int index;

    /** 描述 */
    private String description;

    /**开奖号码个数*/
    private Integer resultNum;

    /** 父彩种 */
    private Game parent;

    /**
     *
     * @param index
     * @param description
     * @param resultNum 开奖号码个数
     * @param parent 父彩种
     */
    private Game(int index, String description, Integer resultNum, Game parent) {
        this.description = description;
        this.index = index;
        this.resultNum = resultNum;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String getLabel() {
        return null;
    }
    
    public String getDescription() {
        return description;
    }

    public Integer getResultNum() {
        return resultNum;
    }

    public Game getParent() {
        return parent;
    }

    public String toString() {
        if (parent != null) {
            return parent.getDescription() + "-" + this.description;
        }
        return this.description;
    }
 
}
