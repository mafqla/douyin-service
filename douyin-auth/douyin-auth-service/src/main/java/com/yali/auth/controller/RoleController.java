package com.yali.auth.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yali.api.client.user.UserClient;
import com.yali.api.dto.auth.RoleDTO;
import com.yali.auth.domain.dto.PrivilegeDTO;
import com.yali.auth.domain.po.Privilege;
import com.yali.auth.domain.po.Role;
import com.yali.auth.service.IRoleService;
import com.yali.common.domain.dto.PageDTO;
import com.yali.common.domain.query.PageQuery;
import com.yali.common.exceptions.BadRequestException;
import com.yali.common.exceptions.CommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "角色管理")
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;
    private final UserClient userClient;

    @ApiOperation("分页查询角色列表")
    @GetMapping("/list")
    public PageDTO<RoleDTO> listAllRoles(PageQuery pageQuery) {
        // 1.分页查询
        Page<Role> page = roleService.listRolesByPage(pageQuery);
        // 2.非空判断
        List<Role> list = page.getRecords();
        if (CollectionUtil.isEmpty(list)) {
            // 结果为空，返回空结果 添加总页码数
            return new PageDTO<>(page.getTotal(), page.getPages(), Collections.emptyList());
        }
        // 3.数据转换
        List<RoleDTO> dtoList = list.stream().map(
                role -> {
                    RoleDTO roleDTO = role.toDTO();
                    roleDTO.setCreater(userClient.queryUserById(role.getCreater()).getNickname());
                    roleDTO.setUpdater(userClient.queryUserById(role.getUpdater()).getNickname());
                    return roleDTO;
                }

        ).collect(Collectors.toList());
        // 4.封装返回
        return new PageDTO<>(page.getTotal(), page.getPages(), dtoList);
    }

    @ApiOperation("查询角色列表")
    @GetMapping
    public List<RoleDTO> listStaffRoles() {
        // 1.查询
        List<Role> list = roleService.lambdaQuery().eq(Role::getType, Role.RoleType.CUSTOM).list();
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }
        // 3.数据转换
        return list.stream().map(Role::toDTO).collect(Collectors.toList());
    }

    @ApiOperation("根据id查询角色")
    @GetMapping("/{id}")
    public RoleDTO queryRoleById(@PathVariable("id") Long id) {
        // 1.查询
        Role role = roleService.getById(id);
        if (role == null) {
            return null;
        }
        // 2.数据转换
        return role.toDTO();
    }


    @ApiOperation("新增角色")
    @PostMapping
    public void saveRole(RoleDTO roleDTO) {
        roleService.saveRole(roleDTO);
    }

    @ApiOperation("修改角色信息")
    @PutMapping("{id}")
    public void updateRole(
            @RequestBody RoleDTO editRole,
            @ApiParam(value = "角色id", example = "1") @PathVariable("id") Long id
    ) {
        roleService.updateRole(editRole, id);
    }

    @ApiOperation("删除角色信息")
    @DeleteMapping("{id}")
    public void deleteRole(@ApiParam(value = "角色id", example = "1") @PathVariable("id") Long id) {
        roleService.deleteRole(id);
    }
}
