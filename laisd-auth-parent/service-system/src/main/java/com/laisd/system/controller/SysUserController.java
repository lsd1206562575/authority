package com.laisd.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laisd.common.result.Result;
import com.laisd.common.utils.MD5;
import com.laisd.model.system.SysUser;
import com.laisd.model.vo.SysUserQueryVo;
import com.laisd.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author laisd
 * @since 2023-08-30
 */
@Api(tags = "UserManagement_Interface")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    //条件分页查询接口
    @ApiOperation("ConditionalPaginationQuery_Interface")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysUserQueryVo sysUserQueryVo) {

        //创建page对象
        Page<SysUser> pageParam = new Page<>(page, limit);
        //调用service方法
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam, sysUserQueryVo);
        return Result.ok(pageModel);
    }

    //添加用户接口
    @ApiOperation("AddUser_Interface")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser sysUser) {
        String encrypt = MD5.encrypt(sysUser.getPassword());
        sysUser.setPassword(encrypt);

        boolean isSuccess = sysUserService.save(sysUser);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 根据id查询
    @ApiOperation("findById")
    @PostMapping("/getUser/{id}")
    public Result getUser(@PathVariable Long id) {
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    // 修改用户接口
    @ApiOperation(value = "UpdateUser_Interface")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysUser sysUser) {
        boolean isSuccess = sysUserService.updateById(sysUser);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 删除用户接口
    @ApiOperation("LogicalDelete_Interface")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        boolean isSuccess = sysUserService.removeById(id);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "updateStatus")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        sysUserService.updateStatus(id, status);
        return Result.ok();
    }

}

