package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description 用户管理
 * @Date 2023.1.6 22:58
 * @Author fuqianlin
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByEmail(String email);

    /**
     * 根据用户id删除用户
     *
     * @param id      用户id
     * @param isValid 0:删除 1:正常
     * @return int 返回受影响的行数
     */
    @Modifying
    @Query(value = "update user set  is_valid= ?2 where id = ?1", nativeQuery = true)
    int deleteById(Integer id, byte isValid);

    /**
     * 根据用户名查找所有用户
     * @param username 用户名
     * @param isValid 用户是否有效(1无效,0有效)
     * @param Type 用户类型 0：普通用户，1：管理员，2：超级管理员，3：VIP用户
     * @return List<UserEntity>
     */
    @Query(value = "select * from user where username = ?1 and is_valid = ?2 and type=?3", nativeQuery = true)
    List<UserEntity> findAllUser(String username, int isValid, int Type);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return UserEntity
     */
    UserEntity findTopByEmail(String email);

    UserEntity findById(Integer userId);

    /**
     * 根据用户email重置密码
     * @param email 用户email
     * @param password 用户密码
     * @return int 返回受影响的行数
     */
    @Modifying
    @Query(value = "update user set password = ?2 where email = ?1", nativeQuery = true)
    int resetPasswordByEmail(String email, String password);


    /**
     * 根据用户邮箱修改头像
     *
     * @param email  用户邮箱
     * @param avatar 头像
     * @return int
     */
    @Modifying
    @Query(value = "update user set user_avatar = ?2 where email = ?1", nativeQuery = true)
    int updateAvatarByEmail(String email, String avatar);

    /**
     * 更新用户信息
     *
     * @param email     用户邮箱
     * @param username  用户名
     * @param userNum   用户账号
     * @param phone     手机号码
     * @param gender    性别
     * @param birthdate 出生日期
     * @param signature 个性签名
     * @param school    学校
     * @param location  位置
     * @param uIP       用户ip
     */
    @Modifying
    @Query(value = "UPDATE user SET " +
            "username = COALESCE(NULLIF(?2, ''), username), " +
            "user_num = COALESCE(NULLIF(?3, ''), user_num), " +
            "phone = COALESCE(NULLIF(?4, ''), phone), " +
            "gender = COALESCE(NULLIF(?5, ''), gender), " +
            "birthdate = COALESCE(NULLIF(?6, ''), birthdate), " +
            "signature = COALESCE(NULLIF(?7, ''), signature), " +
            "school = COALESCE(NULLIF(?8, ''), school), " +
            "location = COALESCE(NULLIF(?9, ''), location), " +
            "u_ip = COALESCE(NULLIF(?10, ''), u_ip) " +
            "WHERE email = ?1", nativeQuery = true)
    void updateUserInfoByEmail(String email, String username, String userNum, String phone,
                               String gender, String birthdate, String signature,
                               String school, String location, String uIP);

    /**
     * 分页查询用户列表
     *
     * @param page 页码
     * @param size 每页数量
     */
    @Query(value = "select * from user  limit ?1,?2", nativeQuery = true)
    List<UserEntity> findAllUserByPage(Integer page, Integer size);



}


