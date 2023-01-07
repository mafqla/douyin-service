package com.yali.vilivili.service;

import com.yali.vilivili.model.User;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.deleteByUserIdRO;
import com.yali.vilivili.model.ro.updateAndSaveUserRO;

import java.util.List;

/**
 * @Description 用户管理
 * @Date 2023.1.6 22:58
 * @Author fuqianlin
 */
public interface UserService {


    /**
     * 更新和保存用户
     * @param ro
     */
    void updateAndSaveUser(updateAndSaveUserRO ro);


    /**
     * 删除用户
     * @param ro
     */
    void deleteById(deleteByUserIdRO ro);
    List<User> findAllUser(UserSelectRO ro);
}
