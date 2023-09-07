package com.laisd.system.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laisd.common.result.Result;
import com.laisd.common.result.ResultCodeEnum;
import com.laisd.common.utils.JwtHelper;
import com.laisd.common.utils.ResponseUtil;
import com.laisd.model.vo.LoginVo;
import com.laisd.system.custom.CustomUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    //构造
    public TokenLoginFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/system/index/login","POST"));
    }

    //获取用户名和密码，认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginVo loginVo =
                    new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //认证成功调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        //获取认证对象
        CustomUser customUser = (CustomUser)auth.getPrincipal();

        //生成token
        String token =
                JwtHelper.createToken(customUser.getSysUser().getId(),
                                      customUser.getSysUser().getUsername());

        //返回
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        ResponseUtil.out(response, Result.ok(map));
    }

    //认证失败
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if(e.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 204, e.getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_MOBLE_ERROR));
        }
    }
}
