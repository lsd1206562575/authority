package com.laisd.system.test;

import com.laisd.model.system.SysRole;
import com.laisd.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    //1.查询表所有记录
    @Test
    public void findALl() {
        List<SysRole> list = sysRoleMapper.selectList(null);
        for (SysRole sysRole : list) {
            System.out.println(sysRole);
        }
    }

    //2.添加操作
    @Test
    public void add() {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试角色3");
        sysRole.setRoleCode("testManager3");
        sysRole.setDescription("测试角色");
        int rows = sysRoleMapper.insert(sysRole);
        System.out.println(rows);
    }

    //3.修改操作
    @Test
    public void update() {
        SysRole sysRole = sysRoleMapper.selectById(1);

        sysRole.setDescription("系统管理员laisd");

        sysRoleMapper.updateById(sysRole);
    }

}
