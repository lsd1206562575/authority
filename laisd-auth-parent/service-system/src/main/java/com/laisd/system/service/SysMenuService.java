package com.laisd.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.laisd.model.system.SysMenu;
import com.laisd.model.vo.AssginMenuVo;
import com.laisd.model.vo.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author laisd
 * @since 2023-09-04
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(String id);

    List<SysMenu> findSysMenuByRoleId(String roleId);

    void doAssign(AssginMenuVo assginMenuVo);

    List<RouterVo> getUserMenuList(String id);

    List<String> getUserButtonList(String id);
}
