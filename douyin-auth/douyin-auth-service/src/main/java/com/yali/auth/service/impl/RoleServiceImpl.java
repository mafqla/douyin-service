package com.yali.auth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.api.dto.auth.RoleDTO;
import com.yali.auth.domain.po.Role;
import com.yali.auth.mapper.RoleMapper;
import com.yali.auth.service.IRoleMenuService;
import com.yali.auth.service.IRolePrivilegeService;
import com.yali.auth.service.IRoleService;
import com.yali.auth.util.PrivilegeCache;
import com.yali.common.domain.query.PageQuery;
import com.yali.common.exceptions.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.yali.auth.common.constants.AuthErrorInfo.Code.*;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final IRoleMenuService roleMenuService;
    private final IRolePrivilegeService rolePrivilegeService;
    private final PrivilegeCache privilegeCache;

    @Override
    public Page<Role> listRolesByPage(PageQuery pageQuery) {
        return query()
                .orderBy(pageQuery.getSortBy() != null, pageQuery.getIsAsc(), pageQuery.getSortBy())
                .page(new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize()));
    }

    @Override
    public boolean exists(Long roleId) {
        int count = Math.toIntExact(lambdaQuery().eq(Role::getId, roleId).count());
        return count > 0;
    }

    @Override
    public boolean exists(List<Long> roleIds) {
        int count = Math.toIntExact(lambdaQuery().in(Role::getId, roleIds).count());
        return count != roleIds.size();
    }

    @Override
    public Role queryRoleById(Long id) {
        return getById(id);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        // 1.删除角色
        removeById(id);
        // 2.删除角色与权限的关联信息
        roleMenuService.removeByRoleId(id);
        rolePrivilegeService.removeByRoleId(id);
        // 3.清理缓存
        privilegeCache.removeCacheByRoleId(id);
    }

    @Override
    public void saveRole(RoleDTO roleDTO) {
        validateRole(roleDTO);

        Role role = convertToRole(roleDTO);
        save(role);
    }

    @Override
    public void updateRole(RoleDTO roleDTO, Long id) {
        validateRoleForUpdate(roleDTO, id);

        Role role = convertToRole(roleDTO);
        role.setId(id);
        updateById(role);
    }


    private void validateRole(RoleDTO roleDTO) {
        //1. 选判断角色和id是否存在
        if (exists(roleDTO.getId())) {
            throw new CommonException(ROLE_ID_EXISTS_CODE, "角色id已存在");
        }
        if (lambdaQuery().eq(Role::getName, roleDTO.getName()).count() > 0) {
            throw new CommonException(ROLE_NAME_EXISTS_CODE, "角色名称已存在");
        }
    }

    private void validateRoleForUpdate(RoleDTO roleDTO, Long id) {
        // 检查角色是否存在
        if (!exists(id)) {
            throw new CommonException(ROLE_NOT_FOUND_CODE, "角色id不存在");
        }

        // 检查更新后的角色名是否在数据库中有相同的（排除当前角色）
        String updatedRoleName = roleDTO.getName();
        if (lambdaQuery().ne(Role::getId, id).eq(Role::getName, updatedRoleName).count() > 0) {
            throw new CommonException(ROLE_NAME_EXISTS_CODE, "更新后的角色名与已有角色重复");
        }
    }


    private Role convertToRole(RoleDTO roleDTO) {
        Role role = new Role(roleDTO);
        Role.RoleType roleType = Role.RoleType.values()[roleDTO.getType()];
        role.setType(roleType);
        return role;
    }
}
