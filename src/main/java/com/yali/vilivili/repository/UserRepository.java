package com.yali.vilivili.repository;

import com.yali.vilivili.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description
 * @Date 2023/1/3 21:36
 * @Author pq
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findTopByUsername(String username);

    @Query(value = "select * from user where username = ?1 and is_valid = ?2 and type=?3",nativeQuery = true)
    List<User> findAllUser(String username, int isValid, int Type);
}
