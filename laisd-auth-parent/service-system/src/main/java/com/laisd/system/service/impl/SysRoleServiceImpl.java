package com.laisd.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laisd.model.system.SysRole;
import com.laisd.model.vo.SysRoleQueryVo;
import com.laisd.system.mapper.SysRoleMapper;
import com.laisd.system.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {

        return baseMapper.selectPage(pageParam, sysRoleQueryVo);
    }

}
