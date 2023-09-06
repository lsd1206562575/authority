package com.laisd.system.controller;

import com.laisd.common.result.Result;
import com.laisd.common.result.ResultCodeEnum;
import com.laisd.common.utils.JwtHelper;
import com.laisd.common.utils.MD5;
import com.laisd.model.system.SysUser;
import com.laisd.model.vo.LoginVo;
import com.laisd.system.exception.laisdException;
import com.laisd.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台登录登出
 * </p>
 */
@Api(tags = "Login_Interface")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("UserLogin_Interface")
    public Result login(@RequestBody LoginVo loginVo) {
        SysUser sysUser = sysUserService.getUserInfoByUserName(loginVo.getUsername());
        if(sysUser == null) {
            throw new laisdException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        if(!MD5.encrypt(loginVo.getPassword()).equals(sysUser.getPassword())) {
            throw new laisdException(ResultCodeEnum.PASSWORD_ERROR);
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new laisdException(ResultCodeEnum.ACCOUNT_STOP);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("UserInfo_Interface")
    public Result info(HttpServletRequest request) {

        String token = request.getHeader("token");

        String username = JwtHelper.getUsername(token);

        Map<String, Object> map = sysUserService.getUserInfo(username);

        return Result.ok(map);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("UserLogout_Interface")
    public Result logout() {
        return Result.ok();
    }
}