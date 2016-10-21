/*
 * Copyright 2015-2020 msun.com All right reserved.
 */
package com.msun.wxbet.support.interceptor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lamfire.json.JSON;
import com.lamfire.utils.StringUtils;
import com.msun.wxbet.cons.Definition;
import com.msun.wxbet.persistence.model.User;
import com.msun.wxbet.persistence.service.UserService;

/**
 * @author zxc Sep 19, 2016 3:08:55 PM
 */
@Component
public class WechatAuthorizationInterceptor extends HandlerInterceptorAdapter implements Definition {

    static String         AUTHORIZE    = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo#wechat_redirect";
    static String         ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    static String         USER_INFO    = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    @Value("${appId}")
    private String        appId;
    @Value("${appSecret}")
    private String        appSecret;
    @Value("${baseUrl}")
    private String        baseUrl;

    @Autowired
    protected UserService userService;

    /**
     * <pre>
     * 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
     * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
     * 
     * 1. 如果参数中包含code且Session中openId为空，那么说明是微信回调的URL，通过code获取openId，存入Session
     * 2. 如果Session中不包含openId，则需要跳转到微信授权页面
     * 3. 如果Session中包含openId，说明已授权，不需要做任何处理
     * </pre>
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String code = request.getParameter("code");
        String openId = (String) request.getSession(true).getAttribute(OPENID_SESSION_KEY);

        if (openId == null && code != null) {
            String url = String.format(ACCESS_TOKEN, appId, appSecret, code);
            JSON json = JSON.fromJSONString(IOUtils.toString(new URL(url), "UTF-8"));
            _.info("[wechat access_token] " + json);
            if (!json.containsKey("openid")) throw new RuntimeException("获取不到用户OpenId: " + json);
            openId = json.getString("openid");
            String access_token = json.getString("access_token");
            wechatUser(openId, access_token);
            request.getSession(true).setAttribute(OPENID_SESSION_KEY, openId);
            return true;
        } else if (openId == null) {
            String backUrl = baseUrl + request.getRequestURI();
            if (request.getQueryString() != null) backUrl += "?" + request.getQueryString();
            String url = String.format(AUTHORIZE, appId, URLEncoder.encode(backUrl, "UTF-8"), code);
            _.info("[wechat authorize] " + url);
            response.sendRedirect(url);
            return false;
        } else {
            return true;
        }
    }

    /**
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * 
     * <pre>
     * {
     *     "subscribe": 1, 
     *     "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
     *     "nickname": "Band", 
     *     "sex": 1, 
     *     "language": "zh_CN", 
     *     "city": "广州", 
     *     "province": "广东", 
     *     "country": "中国", 
     *     "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0", 
     *     "subscribe_time": 1382694957,
     *     "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     *     "remark": "",
     *     "groupid": 0
     * }
     * </pre>
     * 
     * @param json
     * @throws IOException
     * @throws MalformedURLException
     */
    public void wechatUser(String openId, String access_token) throws Exception {
        String url = String.format(USER_INFO, access_token, openId);
        JSON json = JSON.fromJSONString(IOUtils.toString(new URL(url), "UTF-8"));
        _.info("[wechat user_info] " + json);

        String nickname = json.getString("nickname");
        String img = json.getString("headimgurl");
        User user = userService.getUserByOpenIdIfExist(openId);
        if (user == null) user = new User(openId);
        if (StringUtils.isNotEmpty(nickname)) user.setNickname(nickname);
        if (StringUtils.isNotEmpty(img)) user.setAvatar(img);
        user.setCreateTime(new Date());
        userService.save(user);
    }
}
