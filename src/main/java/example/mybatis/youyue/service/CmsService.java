package example.mybatis.youyue.service;

import com.ajaxjson.JSONMessage;
import com.util.DateUtils;
import example.mybatis.youyue.domain.Cms;
import example.mybatis.youyue.mapper.CmsCommentMapper;
import example.mybatis.youyue.mapper.CmsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 15/8/26.
 */
@RestController
@RequestMapping("/youyue/cms")
public class CmsService {

    private transient static final Logger logger = LoggerFactory.getLogger(CmsService.class);
    @Autowired private CmsMapper cmsMapper;
    @Autowired private CmsCommentMapper cmsCommentMapper;
    @Autowired private CmsCommentService cmsCommentService;

    @RequestMapping(value={"/add"}, method={RequestMethod.POST})
    public JSONMessage addCms(@ModelAttribute("cms") Cms cms) {
        try {
            cms.setLikeCount(0);
            cms.setUnlikeCount(0);
            cmsMapper.insert(cms);
            return JSONMessage.newMessageOnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return JSONMessage.newMessageOnFail().setMsg(e.getMessage());
        }
    }

    @RequestMapping(value={"/channel/{channelName}"}, method={RequestMethod.GET})
    public JSONMessage queryCmsByChannel(@PathVariable("channelName") String channel) {
        List<Cms> cmsList = cmsMapper.queryByChannel(channel);

        List<List<Object>> resultList = new ArrayList<>();
        for (Cms cms : cmsList) {
            List<Object> cmsResult = new ArrayList<>();
            cmsResult.add(cms.getId());
            cmsResult.add(cms.getChannelName());
            cmsResult.add(cms.getTitle());
            int imgCount = 0;
            if (!"".equals(JSONMessage.defaultValue(cms.getImg1(),""))) {
                imgCount++;
            }
            if (!"".equals(JSONMessage.defaultValue(cms.getImg2(),""))) {
                imgCount++;
            }
            if (!"".equals(JSONMessage.defaultValue(cms.getImg3(),""))) {
                imgCount++;
            }
            cmsResult.add(imgCount);
            cmsResult.add(JSONMessage.defaultValue(cms.getHasBigImg(),false));
            cmsResult.add(JSONMessage.defaultValue(cms.getImg1(),""));
            cmsResult.add(JSONMessage.defaultValue(cms.getImg2(),""));
            cmsResult.add(JSONMessage.defaultValue(cms.getImg3(),""));
            cmsResult.add(JSONMessage.defaultValue(cms.getLabel(),""));
            cmsResult.add(JSONMessage.defaultValue(cms.getSource(),""));
            cmsResult.add(JSONMessage.defaultValue(cms.getSourceUrl(),""));
            cmsResult.add(cmsCommentService.queryCommentByCmsId(cms.getId()).size()); //评论数
            cmsResult.add(DateUtils.dateToString(cms.getCreateTime().getTime(), "yyyy-MM-dd HH:mm"));
            cmsResult.add(DateUtils.timeBeforeDesc(cms.getCreateTime().getTime()));
            resultList.add(cmsResult);
        }
        return JSONMessage.newMessageOnSuccess().setResult("list", resultList);
    }

    @RequestMapping(value={"/id/{cmsId}"}, method={RequestMethod.GET})
    public JSONMessage queryCmsById(@PathVariable("cmsId") Integer id) {
        try {
            Cms cms = cmsMapper.selectByPrimaryKey(id);
            List<Object> cmsResult = new ArrayList<>();
            cmsResult.add(cms.getId());
            cmsResult.add(cms.getChannelName());
            cmsResult.add(cms.getTitle());
            cmsResult.add(JSONMessage.defaultValue(cms.getLabel(), ""));
            cmsResult.add(JSONMessage.defaultValue(cms.getSource(),""));
            cmsResult.add(JSONMessage.defaultValue(cms.getSourceUrl(), ""));
            cmsResult.add(DateUtils.dateToString(cms.getCreateTime().getTime(), "MM-dd HH:mm"));
            cmsResult.add(JSONMessage.defaultValue(cms.getContent(), ""));
            cmsResult.add(JSONMessage.defaultValue(cms.getLikeCount(), 0));
            cmsResult.add(JSONMessage.defaultValue(cms.getUnlikeCount(), 0));
            cmsResult.add(cmsCommentService.queryCommentByCmsId(id));
            return JSONMessage.newMessageOnSuccess().setResult("cms", cmsResult);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONMessage.newMessageOnFail();
        }
    }

    @RequestMapping(value={"/like/{cmsId}"}, method={RequestMethod.GET})
    public JSONMessage like(@PathVariable("cmsId") Integer id) {
        try {
            cmsMapper.incrementLikeCount(id);
            Cms cms = cmsMapper.selectByPrimaryKey(id);
            return JSONMessage.newMessageOnSuccess()
                    .setResult("likeCount", cms.getLikeCount())
                    .setResult("unlikeCount", cms.getUnlikeCount());
        } catch(Exception e) {
            e.printStackTrace();
            return JSONMessage.newMessageOnFail();
        }
    }

    @RequestMapping(value={"/unlike/{cmsId}"}, method={RequestMethod.GET})
    public JSONMessage unlike(@PathVariable("cmsId") Integer id) {
        try {
            cmsMapper.incrementUnlikeCount(id);
            Cms cms = cmsMapper.selectByPrimaryKey(id);
            return JSONMessage.newMessageOnSuccess()
                    .setResult("likeCount", cms.getLikeCount())
                    .setResult("unlikeCount", cms.getUnlikeCount());
        } catch(Exception e) {
            e.printStackTrace();
            return JSONMessage.newMessageOnFail();
        }
    }

}
