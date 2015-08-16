package example.mybatis.youyue.service;

import com.ajaxjson.JSONMessage;
import example.mybatis.youyue.domain.User;
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
@RequestMapping("/youyue/user")
public class UserService {

    private transient static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired private UserMapper userMapper;
    @Autowired private PlatformTransactionManager youyueTransactionManager;

    @RequestMapping(value={"/register"}, method = {RequestMethod.GET, RequestMethod.POST})
    public JSONMessage register(@ModelAttribute("user")User user, HttpServletResponse response) {
        if (user.getName() == null || user.getName().trim().equals("")) {
            return JSONMessage.newMessageOnFail().setMsg("用户名不能为空！");
        }
        if (user.getPasswd() == null || user.getPasswd().trim().equals("")) {
            return JSONMessage.newMessageOnFail().setMsg("密码不能为空！");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = youyueTransactionManager.getTransaction(def);
        try {
            user.setEmail("");
            user.setMobile("");
            user.setPhoto("");
            userMapper.insert(user);
            youyueTransactionManager.commit(status);
        } catch (Exception e) {
            youyueTransactionManager.rollback(status);
            return JSONMessage.newMessageOnFail().setMsg("该用户名[" + user.getName() + "]已经被注册过了！");
        }

        return JSONMessage.newMessageOnSuccess().setResult("name111111", user.getName());
    }
}
