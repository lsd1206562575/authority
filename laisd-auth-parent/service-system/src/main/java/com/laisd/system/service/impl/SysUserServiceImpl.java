package com.laisd.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laisd.model.system.SysUser;
import com.laisd.model.vo.RouterVo;
import com.laisd.model.vo.SysUserQueryVo;
import com.laisd.system.mapper.SysUserMapper;
import com.laisd.system.service.SysMenuService;
import com.laisd.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author laisd
 * @since 2023-08-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(pageParam, sysUserQueryVo);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setStatus(status);
        baseMapper.updateById(sysUser);
    }

    @Override
    public SysUser getUserInfoByUserName(String username) {
        return baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {

        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = this.getUserInfoByUserName(username);

        //根据用户id获取菜单权限值
        List<RouterVo> routerVoList = sysMenuService.getUserMenuList(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.getUserButtonList(sysUser.getId());

        result.put("name", sysUser.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        //当前权限控制使用不到，我们暂时忽略
        result.put("roles",  new HashSet<>());

        result.put("routers", routerVoList);
        result.put("buttons", permsList);
        return result;

    }


}
