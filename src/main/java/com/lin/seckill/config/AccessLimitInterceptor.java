package com.lin.seckill.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.seckill.entity.User;
import com.lin.seckill.service.IUserService;
import com.lin.seckill.utils.CookieUtil;
import com.lin.seckill.vo.RespBean;
import com.lin.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * <p>AccessLimit拦截器</p>
 *
 * @author : star
 * @date : 2022/7/30 17:13
 */
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            User user = getUser(request, response);
            UserContext.setUser(user);
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int second = accessLimit.second();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String uri = request.getRequestURI();
            if (needLogin) {
                if (user == null) {
                    render(response, RespBeanEnum.SESSION_ERROR);
                    return false;
                }
                uri += ":" + user.getId();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer count = (Integer) valueOperations.get(uri);
            if (count == null) {
                valueOperations.set(uri, 1, second, TimeUnit.SECONDS);
            } else if (count < maxCount) {
                valueOperations.increment(uri);
            } else {
                render(response, RespBeanEnum.ACCESS_LIMIT_REAHCED);
                return false;
            }
        }
        return true;
    }

    /**
     * 构建返回对象
     *
     * @param response
     * @param respBeanEnum
     */
    private void render(HttpServletResponse response, RespBeanEnum respBeanEnum) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        RespBean respBean = RespBean.error(respBeanEnum);
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @param response
     * @return
     */
    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getCookieValue(request, "userTicket");
        if (StringUtils.isEmpty(ticket)) {
            return null;
        }
        return userService.getUserByCookie(ticket, request, response);
    }
}
