package example.mybatis.youyue.service;

import com.ajaxjson.JSONMessage;
import example.mybatis.youyue.domain.CmsChannel;
import example.mybatis.youyue.mapper.CmsChannelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 悠阅空间服务
 */
@RestController
@RequestMapping("/youyue/channel")
public class CmsChannelService {

    private transient static final Logger logger = LoggerFactory.getLogger(CmsChannelService.class);
    @Autowired private CmsChannelMapper cmsChannelMapper;
    @Autowired private PlatformTransactionManager youyueTransactionManager;

    @RequestMapping(value={"/all"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
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
