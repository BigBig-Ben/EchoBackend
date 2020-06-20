package org.demo.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.demo.Entity.User;
import org.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;


@RestController
public class WeChatLogin {

    private static final long serialVersionUID = 1L;
    private static final String APPID = "wxd5cc140983f0aef6";
    private static final String SECRET = "50216a427617d2d629a14304d11c5e0b";
    private String code;

    @Autowired
    private UserService userService;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //获取凭证校检接口
    @RequestMapping(value = "/api/login", method = RequestMethod.GET)
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {
        //微信的接口
        //System.out.println("login");
        code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID +
                "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        //进行网络请求,访问url接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        //根据返回值进行后续操作
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            //解析从微信服务器获得的openid和session_key;
            String sessionData = responseEntity.getBody();
            JSONObject json = JSONObject.fromObject(sessionData);
            //获取用户的唯一标识
            String openid = json.get("openid").toString();
            //System.out.println(openid);
            //获取会话秘钥
            String session_key = json.get("session_key").toString();
            //下面就可以写自己的业务代码了
            //最后要返回一个自定义的登录态,用来做后续数据传输的验证
            List<User> user = userService.checkOpenid(openid);
            if (user.isEmpty()) {
                json.put("flag", 0);
                User u = new User();
                u.setOpenId(openid);
                userService.save(u);
                user = userService.checkOpenid(openid);
            } else json.put("flag", 1);
            for (User u : user) {
                //System.out.println(u.getId());
                json.put("UserId", u.getId());
            }
            return json;
        }
        return null;
    }
}
 
