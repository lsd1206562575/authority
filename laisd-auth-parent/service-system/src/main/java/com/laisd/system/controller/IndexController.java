package com.laisd.system.controller;

import com.laisd.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 登录
     *
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("UserLogin_Interface")
    public Result login() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin");
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("UserInfo_Interface")
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
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