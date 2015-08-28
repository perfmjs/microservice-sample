package example.mybatis.youyue.service;

import com.ajaxjson.JSONMessage;
import com.util.DateUtils;
import com.util.JSONWebTokenUtils;
import example.mybatis.youyue.domain.CmsComment;
import example.mybatis.youyue.mapper.CmsCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tony on 15/8/16.
 */
@RestController
@RequestMapping("/youyue/comment")
public class CmsCommentService {

    private transient static final Logger logger = LoggerFactory.getLogger(CmsCommentService.class);
    @Autowired private CmsCommentMapper cmsCommentMapper;

    @RequestMapping(value={"/add"}, method={RequestMethod.POST})
    public JSONMessage addComment(@ModelAttribute("comment") CmsComment cmsComment, HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter(JSONWebTokenUtils.JWT_REQUEST_PARAMETER_KEY);
        cmsComment.setLikeCount(0);
        cmsComment.setUserName(JSONWebTokenUtils.splitToken(token)[1]);
        try {
            cmsCommentMapper.insert(cmsComment);
            return JSONMessage.newMessageOnSuccess()
                    .setResult("user", cmsComment.getUserName())
                    .setResult("id", cmsComment.getId())
                    .setResult("createTime", DateUtils.dateToString(new Date(), "MM-dd HH:mm"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSONMessage.newMessageOnFail();
    }


    @RequestMapping(value={"/like/{commentId}"}, method={RequestMethod.GET})
    public JSONMessage likeComment(@PathVariable("commentId") int commentId) {
        try {
            cmsCommentMapper.incrementLikeCount(commentId);
            return JSONMessage.newMessageOnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONMessage.newMessageOnFail();
    }

    @RequestMapping(value={"/cmsId/{cmsId}"}, method={RequestMethod.POST})
    public List<List<Object>> queryCommentByCmsId(@PathVariable("cmsId") int cmsId) {
        List<List<Object>> cmsCommentListResult = new ArrayList<>();
        try {
            List<CmsComment> cmsCommentList = cmsCommentMapper.queryByCmsId(cmsId);
            for (CmsComment cmsComment: cmsCommentList) {
                List<Object> cmsCommentResult = new ArrayList<>();
                cmsCommentResult.add(cmsComment.getId());
                cmsCommentResult.add(cmsComment.getUserName());
                cmsCommentResult.add(JSONMessage.defaultValue(cmsComment.getLikeCount(), 0));
                cmsCommentResult.add(cmsComment.getContent());
                cmsCommentResult.add(DateUtils.dateToString(cmsComment.getCreateTime().getTime(), "MM-dd HH:mm"));
                cmsCommentListResult.add(cmsCommentResult);
            }
        } catch(Exception e) {
            e.printStackTrace();
            cmsCommentListResult = new ArrayList<>();
        }
        return cmsCommentListResult;
    }

}
