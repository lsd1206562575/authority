package com.laisd.system.controller;


import com.laisd.common.result.Result;
import com.laisd.model.system.SysMenu;
import com.laisd.model.system.SysRole;
import com.laisd.model.vo.AssginMenuVo;
import com.laisd.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author laisd
 * @since 2023-09-04
 */
@Api(tags = "MenuManagement")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "GetMenuByRole_Interface")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable String roleId) {
        List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @ApiOperation(value = "AssginMenu")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }


    @ApiOperation("GetMenu_Interface")
    @GetMapping("/findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation("findById_Interface")
    @GetMapping("findNode/{id}")
    public Result findNodeById(@PathVariable String id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        return Result.ok(sysMenu);
    }

    @ApiOperation("AddMenu_Interface")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation("ModifyMenu_Interface")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation(value = "DeleteMenu_Interface")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

}

