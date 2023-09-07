package com.laisd.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.laisd.common.result.ResultCodeEnum;
import com.laisd.model.system.SysMenu;
import com.laisd.model.system.SysRoleMenu;
import com.laisd.model.vo.AssginMenuVo;
import com.laisd.system.exception.laisdException;
import com.laisd.system.mapper.SysMenuMapper;
import com.laisd.system.mapper.SysRoleMenuMapper;
import com.laisd.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laisd.system.utils.MenuHelper;
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



}
