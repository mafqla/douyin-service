package com.yali.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.common.enums.UserType;
import com.yali.user.domain.po.UserDetail;
import com.yali.user.domain.query.UserPageQuery;

import java.util.List;

/**
 * <p>
 * 用户详情表 服务类
 * </p>
 */
public interface IUserDetailService extends IService<UserDetail> {

    UserDetail queryById(Long userId);

    List<UserDetail> queryByIds(List<Long> ids);

    Page<UserDetail> queryUserDetailByPage(UserPageQuery pageQuery, UserType type);

    void updateIpById(Long id, String ipv4);
}
