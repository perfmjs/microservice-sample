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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 悠阅空间服务
 */
@RestController
@RequestMapping("/youyue/query")
public class QueryService {

    private transient static final Logger logger = LoggerFactory.getLogger(QueryService.class);
    @Autowired private CmsChannelMapper cmsChannelMapper;
    @Autowired private PlatformTransactionManager youyueTransactionManager;

    @RequestMapping(value={"/channel"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public JSONMessage queryChannel() {
        try {
            List<CmsChannel> cmsChannelList = cmsChannelMapper.queryAll();
            List<String> channelList = new ArrayList<>();
            for (CmsChannel cmsChannel : cmsChannelList) {
                channelList.add(cmsChannel.getName());
            }
            return JSONMessage.newMessageOnSuccess().setResult("list", channelList);
        } catch (Exception e) {
            return JSONMessage.newMessageOnFail().setMsg(e.getMessage());
        }
    }
}
