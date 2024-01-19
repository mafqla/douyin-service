package com.yali.vilivili.service;

import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.deleteByUserIdRO;
import com.yali.vilivili.model.ro.updateUserRO;
import com.yali.vilivili.model.ro.AddUserRO;
import com.yali.vilivili.model.vo.FileUploadVO;
import com.yali.vilivili.model.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/**
 * @Description 用户管理
 * @Date 2023.1.6 22:58
 * @Author fuqianlin
 */
public interface UserService {


    /**
     * 更新用户信息
     *
     * @param ro 用户信息
     * @return
     */
    void updateUser(updateUserRO ro, MultipartFile avatar);

    /**
     * 查询用户信息
     * @param currentUserId 用户id 可以为空
     * @param userIdToLookup 要查询的用户id
     */
    UserInfoVO findUserById(Integer currentUserId, Integer userIdToLookup);

    /**
     * 添加用户
     *
     * @param ro 用户信息
     */
    void addUser(AddUserRO ro);


    /**
     * 删除用户
     *
     * @param ro 用户id
     */
    void deleteById(deleteByUserIdRO ro);

    List<UserEntity> findUser(UserSelectRO ro);

    /**
     * 分页查询用户列表
     *
     * @param page 页码
     * @param size 每页数量
     */
    List<UserEntity> findAllUserByPage(Integer page, Integer size);


}
