/*
 * Copyright 2015-2020 uuzu.com All right reserved.
 */
package com.msun.wxbet.support.interceptor;

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
import com.msun.wxbet.cons.Definition;
import com.msun.wxbet.persistence.model.User;
import com.msun.wxbet.persistence.service.UserService;

/**
 * @author zxc Sep 19, 2016 3:08:55 PM
 */
@Component
public class WechatAuthorizationInterceptor extends HandlerInterceptorAdapter implements Definition {

    @Value("${appId}")
    private String        appId;
    @Value("${appSecret}")
    private String        appSecret;
    @Value("${baseUrl}")
    private String        baseUrl;

    @Autowired
    protected UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String code = request.getParameter("code");
        String openId = (String) request.getSession(true).getAttribute(OPENID_SESSION_KEY);

        if (openId == null && code != null) {
            // ------------------------
            // 如果参数中包含code且Session中openId为空，那么说明是微信回调的URL，通过code获取openId，存入Session
            // ------------------------
            String checkCodeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
            checkCodeUrl += appId;
            checkCodeUrl += "&secret=";
            checkCodeUrl += appSecret;
            checkCodeUrl += "&code=";
            checkCodeUrl += code;
            checkCodeUrl += "&grant_type=authorization_code";
            String json = IOUtils.toString(new URL(checkCodeUrl), "UTF-8");
            JSON jsonObject = JSON.fromJSONString(json);

            _.info("[wechat oauth2] " + jsonObject);
            if (jsonObject.containsKey("openid")) {
                openId = jsonObject.getString("openid");
                saveUser(jsonObject);
                request.getSession(true).setAttribute(OPENID_SESSION_KEY, openId);
                return true;
            } else {
                throw new RuntimeException("获取不到用户OpenId: " + json);
            }
        } else if (openId == null) {
            // ------------------------
            // 如果Session中不包含openId，则需要跳转到微信授权页面
            // ------------------------
            String backUrl = baseUrl + request.getRequestURI();
            if (request.getQueryString() != null) {
                backUrl += "?" + request.getQueryString();
            }
            /**
             * <pre>
             * 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
             * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
             * </pre>
             */
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
            url += appId;
            url += "&redirect_uri=";
            url += URLEncoder.encode(backUrl, "UTF-8");
            url += "&response_type=code&scope=snsapi_userinfo#wechat_redirect";
            _.info("[wechat get openId] " + url);
            response.sendRedirect(url);
            return false;
        } else {
            // ------------------------
            // 如果Session中包含openId，说明已授权，不需要做任何处理
            // ------------------------
            return true;
        }
    }

    /**
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
     */
    private void saveUser(JSON json) {
        String openid = json.getString("openid");
        String nickname = json.getString("nickname");
        String headimgurl = json.getString("headimgurl");
        User user = userService.getUserByOpenIdIfExist(openid);
        if (user == null) user = new User(openid);
        user.setNickname(nickname);
        user.setAvatar(headimgurl);
        user.setCreateTime(new Date());
        userService.save(user);
    }
}
