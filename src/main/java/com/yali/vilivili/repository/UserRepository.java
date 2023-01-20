package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.UserEntity;
import io.vavr.collection.Traversable;
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
     * @param id
     * @param isValid
     * @return int 返回受影响的行数
     */
    @Modifying
    @Query(value = "update user set  is_valid= ?2 where id = ?1", nativeQuery = true)
    int deleteById(Integer id, byte isValid);

    @Query(value = "select * from user where username = ?1 and is_valid = ?2 and type=?3", nativeQuery = true)
    List<UserEntity> findAllUser(String username, int isValid, int Type);

    UserEntity findTopByUsername(String username);

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
     * @param email 用户邮箱
     * @param avatar  头像
     * @return int
     */
    @Modifying
    @Query(value = "update user set user_avatar = ?2 where email = ?1", nativeQuery = true)
    int updateAvatarByEmail(String email, String avatar);

}


