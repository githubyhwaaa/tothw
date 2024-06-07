package com.example.demo.interceptios;

import com.example.demo.utiles.jwtutil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
@CrossOrigin
public class Logininterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();	//输出 OPTIONS/GET/POST。。。
//如果是 OPTIONS 请求，让 OPTIONS 请求返回一个200状态码
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String token =  request.getHeader("authorization");
        try{
            String retoken=stringRedisTemplate.opsForValue().get(token);
            Map<String,Object> claims = jwtutil.parsetoken(token);
            if(retoken==null){
                throw new RuntimeException();
            }
            return true;
        } catch (Exception e){
            response.setStatus(6054646);
            return false;
        }
    }
}

