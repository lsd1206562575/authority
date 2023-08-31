package com.laisd.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laisd.common.result.Result;
import com.laisd.model.system.SysRole;
import com.laisd.model.vo.SysRoleQueryVo;
import com.laisd.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "RoleManagement_Interface")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //1.查询所有记录
    @ApiOperation("SelectAll_Interface")
    @GetMapping("/findAll")
    public Result findAllRole() {
        List<SysRole> roleList = sysRoleService.list();
        return Result.ok(roleList);
    }

    //2.逻辑删除接口
    @ApiOperation("LogicalDelete_Interface")
    @DeleteMapping("/remove/{id}")
    public Result removeRole(@PathVariable Long id) {
        boolean isSuccess = sysRoleService.removeById(id);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //3.条件分页查询接口
    @ApiOperation("ConditionalPaginationQuery_Interface")
    @GetMapping("/{page}/{limit}")
    public Result findPageQueryRole(@PathVariable Long page,
                                    @PathVariable Long limit,
                                    SysRoleQueryVo sysRoleQueryVo) {

        //创建page对象
        Page<SysRole> pageParam = new Page<>(page, limit);
        //调用service方法
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam, sysRoleQueryVo);
        return Result.ok(pageModel);
    }

    //4.添加角色接口
    @ApiOperation("AddRole_Interface")
    @PostMapping("/save")
    public Result save(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.save(sysRole);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    // 修改-根据id查询
    @ApiOperation("findById")
    @PostMapping("findRoleById/{id}")
    public Result findRoleById(@PathVariable Long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    //5.修改角色接口
    @ApiOperation(value = "UpdateRole_Interface")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysRole sysRole) {
        boolean isSuccess = sysRoleService.updateById(sysRole);
        if (isSuccess) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "BatchRemoveRole_Interface")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        sysRoleService.removeByIds(idList);
        return Result.ok();
    }

}
