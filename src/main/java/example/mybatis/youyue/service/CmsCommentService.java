package example.mybatis.youyue.service;

import com.ajaxjson.JSONMessage;
import example.mybatis.youyue.domain.CmsComment;
import example.mybatis.youyue.mapper.CmsCommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by tony on 15/8/16.
 */
@RestController
@RequestMapping("/youyue/comment")
public class CmsCommentService {
    private transient static final Logger logger = LoggerFactory.getLogger(CmsCommentService.class);
    @Autowired
    private CmsCommentMapper cmsCommentMapper;
    @Autowired private PlatformTransactionManager youyueTransactionManager;

    @RequestMapping(value={"/add"}, method = {RequestMethod.GET, RequestMethod.POST})
    public JSONMessage addComment(@ModelAttribute("comment") CmsComment cmsComment, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        return JSONMessage.newMessageOnFail();
    }
}
