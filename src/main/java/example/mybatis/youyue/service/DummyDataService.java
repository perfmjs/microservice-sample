package example.mybatis.youyue.service;

import com.ajaxjson.JSONMessage;
import example.mybatis.youyue.domain.CmsChannel;
import example.mybatis.youyue.domain.User;
import example.mybatis.youyue.mapper.CmsChannelMapper;
import example.mybatis.youyue.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 悠阅空间服务
 */
@RestController
@RequestMapping("/youyue/dummyData")
public class DummyDataService {

    private transient static final Logger logger = LoggerFactory.getLogger(DummyDataService.class);
    @Autowired private CmsChannelMapper cmsChannelMapper;
    @Autowired private PlatformTransactionManager youyueTransactionManager;

    @RequestMapping(value={"/channel"}, method = {RequestMethod.GET})
    public JSONMessage register(HttpServletResponse response) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = youyueTransactionManager.getTransaction(def);
        try {
            CmsChannel cmsChannel = new CmsChannel();
            cmsChannel.setName("推荐");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("社会");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("娱乐");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("科技");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("汽车");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("体育");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("财经");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("军事");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("国际");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("时尚");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("游戏");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("热点");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("历史");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("美食");
            cmsChannelMapper.insert(cmsChannel);
            cmsChannel.setName("探索");
            cmsChannelMapper.insert(cmsChannel);
            youyueTransactionManager.commit(status);
        } catch (Exception e) {
            youyueTransactionManager.rollback(status);
            return JSONMessage.newMessageOnFail().setMsg(e.getMessage());
        }

        return JSONMessage.newMessageOnSuccess();
    }
}
