package com.yali.user.constants;

import java.time.Duration;

/**
 * @author fuqianlin
 * @date 2023-11-14 14:36
 **/
public interface UserConstants {

    String DEFAULT_PASSWORD = "123456";


    // 验证码的Redis key前缀
    String USER_VERIFY_CODE_KEY = "sms:user:code:phone:";

    // 验证码有效期，5分钟
    Duration USER_VERIFY_CODE_TTL = Duration.ofMinutes(5);
}
