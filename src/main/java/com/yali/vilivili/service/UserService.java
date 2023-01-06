package com.yali.vilivili.service;

import com.yali.vilivili.model.User;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.updateAndSaveUserRO;

import java.util.List;

/**
 * @Description
 * @Date 2023/1/3 21:35
 * @Author pq
 */
public interface UserService {


    /**
     * 更新和保存用户
     * @param ro
     */
    void updateAndSaveUser(updateAndSaveUserRO ro);
    List<User> findAllUser(UserSelectRO ro);
}
