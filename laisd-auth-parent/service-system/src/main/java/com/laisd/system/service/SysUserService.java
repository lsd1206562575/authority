package com.laisd.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.laisd.model.system.SysUser;
import com.laisd.model.vo.SysUserQueryVo;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author laisd
 * @since 2023-08-30
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> pageParam, SysUserQueryVo sysUserQueryVo);

    void updateStatus(Long id, Integer status);

    SysUser getUserInfoByUserName(String username);

    Map<String, Object> getUserInfo(String username);
}
