package com.yali.vilivili.service;

import com.yali.vilivili.model.User;
import com.yali.vilivili.model.ro.UserAddRO;
import com.yali.vilivili.model.ro.UserSelectRO;

import java.util.List;

/**
 * @Description
 * @Date 2023/1/3 21:35
 * @Author pq
 */
public interface UserService {

    void saveUser(UserAddRO ro);

    List<User> findAllUser(UserSelectRO ro);
}
