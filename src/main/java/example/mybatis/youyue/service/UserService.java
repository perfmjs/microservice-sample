package example.mybatis.youyue.service;

import com.ajaxjson.JSONMessage;
import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import example.mybatis.youyue.domain.User;
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
import java.io.IOException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * 悠阅空间服务
 */
@RestController
@RequestMapping("/youyue/user")
public class UserService {

    private transient static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired private UserMapper userMapper;
    @Autowired private PlatformTransactionManager youyueTransactionManager;

    @RequestMapping(value={"/checkToken"}, method={RequestMethod.POST})
    public JSONMessage checkToken(@RequestParam(value="x-access-token") String jwt) {
        try {
            jwt = URLDecoder.decode(jwt, "UTF-8");
        } catch(Exception e) {
            return JSONMessage.newMessageOnFail();
        }
        if (jwt != null && !"".equals(jwt) && jwt.split("\\|").length > 1) {
            String[] jwts = jwt.split("\\|");
            String token = jwts[0];
            String userName = jwts[1];
            try {
                Map<String,Object> decodedPayload =  new JWTVerifier("secretCommonLogin" + userName).verify(token);
                if (decodedPayload.get("iss") != null && decodedPayload.get("iss").equals(userName)) {
                    return JSONMessage.newMessageOnSuccess();
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | IOException | SignatureException | JWTVerifyException e) {
                e.printStackTrace();
            }
        }
        return JSONMessage.newMessageOnFail();
    }

    @RequestMapping(value={"/login"}, method = {RequestMethod.POST})
    public JSONMessage login(@ModelAttribute("user")User user, HttpServletResponse response) {
        if (user.getName() == null || user.getName().trim().equals("")) {
            return JSONMessage.newMessageOnFail().setMsg("用户名不能为空！");
        }
        if (user.getPasswd() == null || user.getPasswd().trim().equals("")) {
            return JSONMessage.newMessageOnFail().setMsg("密码不能为空！");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");

        User loginUser = userMapper.selectByNameAndPasswd(user.getName(), user.getPasswd());
        if (loginUser != null) {
            return JSONMessage.newMessageOnSuccess()
                    .setResult("token", buildJSONWebToken(loginUser));
        }

        return JSONMessage.newMessageOnFail().setMsg("请输入正确的用户名和密码！");
    }

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
            return JSONMessage.newMessageOnSuccess()
                    .setResult("token", buildJSONWebToken(user));
        } catch (Exception e) {
            youyueTransactionManager.rollback(status);
            return JSONMessage.newMessageOnFail().setMsg("该用户名[" + user.getName() + "]已经被注册过了！");
        }
    }

    private String buildJSONWebToken(User user) {
        JWTSigner signer = new JWTSigner("secretCommonLogin" + user.getName());
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("iss", user.getName());
        claims.put("sub", "commonLogin");
        claims.put("aud", "webapp");
        String token = signer.sign(claims,
                new JWTSigner.Options().setAlgorithm(Algorithm.HS256)
                        .setExpirySeconds(3600*1)
                        .setNotValidBeforeLeeway(5)
                        .setIssuedAt(false)
                        .setJwtId(false)
        );
        return token + '|' + user.getName();
    }
}
