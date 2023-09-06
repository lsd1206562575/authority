package com.laisd.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laisd.common.result.ResultCodeEnum;
import com.laisd.model.system.SysMenu;
import com.laisd.model.system.SysRoleMenu;
import com.laisd.model.vo.AssginMenuVo;
import com.laisd.model.vo.RouterVo;
import com.laisd.system.exception.laisdException;
import com.laisd.system.mapper.SysMenuMapper;
import com.laisd.system.mapper.SysRoleMenuMapper;
import com.laisd.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laisd.system.utils.MenuHelper;
import com.laisd.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author laisd
 * @since 2023-09-04
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        // 获取所有菜单
        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        // 所有菜单转换为要求数据格式
        List<SysMenu> resultList = MenuHelper.buildTree(sysMenuList);
        return resultList;
    }

    @Override
    public void removeMenuById(String id) {
        int count = baseMapper.selectCount(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0) {
            throw new laisdException(ResultCodeEnum.NODE_ERROR);
        }
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(String roleId) {
        //获取所有status为1的权限列表
        List<SysMenu> menuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        //根据角色id获取角色权限
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        //获取该角色已分配的所有权限id
        List<String> roleMenuIds = new ArrayList<>();
        for (SysRoleMenu roleMenu : roleMenus) {
            roleMenuIds.add(roleMenu.getMenuId());
        }
        //遍历所有权限列表
        for (SysMenu sysMenu : menuList) {
            if(roleMenuIds.contains(sysMenu.getId())){
                //设置该权限已被分配
                sysMenu.setSelect(true);
            }else {
                sysMenu.setSelect(false);
            }
        }
        //将权限列表转换为权限树
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        return sysMenus;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //遍历所有已选择的权限id
        for (String menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                //添加新权限
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

    @Override
    public List<RouterVo> getUserMenuList(String userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId.equals("1")) {
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1).orderByAsc("sort_value"));
        } else {
            sysMenuList = baseMapper.findMenuListUserId(userId);
        }
        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);

        //构建路由
        List<RouterVo> routerVoList = RouterHelper.buildRouters(sysMenuTreeList);
        return routerVoList;
    }

    @Override
    public List<String> getUserButtonList(String userId) {
        //超级管理员admin账号id为：1
        List<SysMenu> sysMenuList = null;
        if (userId.equals("1")) {
            sysMenuList = baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        } else {
            sysMenuList = baseMapper.findMenuListUserId(userId);
        }
        //创建返回的集合
        List<String> permissionList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
            if(sysMenu.getType() == 2){
                permissionList.add(sysMenu.getPerms());
            }
        }
        return permissionList;
    }

}
