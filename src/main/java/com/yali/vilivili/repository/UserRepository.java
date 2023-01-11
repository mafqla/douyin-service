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
     * @param id
     * @param isValid
     * @return int 返回受影响的行数
     */
    @Modifying
    @Query(value = "update user set  is_valid= ?2 where id = ?1", nativeQuery = true)
    int deleteById(Integer id, byte isValid);

    @Query(value = "select * from user where username = ?1 and is_valid = ?2 and type=?3",nativeQuery = true)
    List<UserEntity> findAllUser(String username, int isValid, int Type);

    UserEntity findTopByUsername(String username);
}
