package com.yali.api.client.user;


import com.yali.api.client.user.fallback.UserClientFallback;
import com.yali.api.dto.user.LoginFormDTO;
import com.yali.api.dto.user.RegisterFormDTO;
import com.yali.api.dto.user.UserDTO;
import com.yali.common.domain.dto.LoginUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user-service", fallbackFactory = UserClientFallback.class)
public interface UserClient {

    /**
     * 根据手机号查询用户id
     *
     * @param phone 手机号
     * @return 用户id
     */
    @GetMapping("/users/ids")
    Long exchangeUserIdWithPhone(@RequestParam("phone") String phone);

    /**
     * 登录接口
     *
     * @param loginDTO 登录信息
     * @return 用户详情
     */
    @PostMapping("/users/detail")
    LoginUserDTO queryUserDetail(@RequestBody LoginFormDTO loginDTO);

    /**
     * 注册接口
     *
     * @param registerDTO 注册信息
     *                    return 用户id
     */
    @PostMapping("/users/register")
    Map<String, Long> registerByPhone(@RequestBody RegisterFormDTO registerDTO);


    /**
     * 查询用户类型
     *
     * @param id 用户id
     * @return 用户类型, 自定义类型
     */
    @GetMapping("/users/{id}/type")
    Integer queryUserType(@PathVariable("id") Long id);

    /**
     * <h1>根据id批量查询用户信息</h1>
     *
     * @param ids 用户id集合
     * @return 用户集合
     */
    @GetMapping("/users/list")
    List<UserDTO> queryUserByIds(@RequestParam("ids") Iterable<Long> ids);


    /**
     * 根据id查询单个学生信息
     *
     * @param id 用户id
     * @return 学生
     */
    @GetMapping("/users/{id}")
    UserDTO queryUserById(@PathVariable("id") Long id);
}
