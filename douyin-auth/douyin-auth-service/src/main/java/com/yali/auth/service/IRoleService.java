package com.yali.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.api.dto.auth.RoleDTO;
import com.yali.auth.domain.po.Role;
import com.yali.common.domain.query.PageQuery;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 */
public interface IRoleService extends IService<Role> {

    Page<Role> listRolesByPage(PageQuery pageQuery);

    boolean exists(Long roleId);

    boolean exists(List<Long> roleIds);

    //根据id查询角色
    Role queryRoleById(Long id);

    void deleteRole(Long id);

    void saveRole(RoleDTO roleDTO);

    //更新角色
    void updateRole(RoleDTO roleDTO, Long id);
}
