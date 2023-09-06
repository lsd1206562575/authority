package com.laisd.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laisd.model.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author laisd
 * @since 2023-09-04
 */

@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListUserId(@Param("userId") String userId);
}
