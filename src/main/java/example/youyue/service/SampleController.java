package example.youyue.service;

import com.ajaxjson.JSONMessage;
import com.github.dbutils.mybatis.extend.ExtendFeaturesMap;
import com.github.dbutils.mybatis.utils.BitUtil;
import example.mybatis.lottery.domain.BetGameIssues;
import example.mybatis.lottery.enums.Game;
import example.mybatis.lottery.enums.IssueStatus;
import example.mybatis.lottery.mapper.BetGameIssuesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

/**
 * Created by tony on 15/7/7.
 */
@RestController
@RequestMapping("/sample")
public class SampleController {

    private transient static final Logger logger = LoggerFactory.getLogger(SampleController.class);
    @Autowired private BetGameIssuesMapper betGameIssuesMapper;
    @Autowired private PlatformTransactionManager lotteryTransactionManager;

    @RequestMapping("/home")
    public String home() {
        return "Hello World";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public JSONMessage insert() {
        BetGameIssues betGameIssues = new BetGameIssues();
        betGameIssues.setIssueNo("1234456");
        betGameIssues.setGameId(Game.FC_3D);
        betGameIssues.setIsCurrent(true);
        betGameIssues.setStartTime(Calendar.getInstance());
        betGameIssues.setEndTime(Calendar.getInstance());
        betGameIssues.setOpenTime(Calendar.getInstance());
        betGameIssues.setStatus(IssueStatus.SALEING);
        betGameIssues.setBitFlag(BitUtil.convert(111));
        ExtendFeaturesMap<String, String> extendFeatures = new ExtendFeaturesMap();
        extendFeatures.put("aaa", "bbb");
        betGameIssues.setExtendFeatures(extendFeatures);

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = lotteryTransactionManager.getTransaction(def);
        try {
            //execute sql using transaction
            int effectedRows = betGameIssuesMapper.insert(betGameIssues);
            lotteryTransactionManager.commit(status);
            return JSONMessage.newMessageOnSuccess()
                    .setCode(200)
                    .setResult("insertedId", betGameIssues.getId())
                    .setResult("effectedRows", effectedRows);
        } catch (Exception e) {
            lotteryTransactionManager.rollback(status);
            logger.error("insert rollback", e);
        }

        return JSONMessage.newMessageOnFail();
    }

}


