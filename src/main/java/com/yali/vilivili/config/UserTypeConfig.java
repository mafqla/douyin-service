package com.yali.vilivili.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型定义
 *
 * @author fuqianlin
 * @date 2023-09-25 00:24
 **/
public class UserTypeConfig {
    private static final Map<Integer, String> userTypeMap = new HashMap<>();

    static {
        userTypeMap.put(0, "normal_user");
        userTypeMap.put(1, "admin");
        userTypeMap.put(2, "super_admin");
        userTypeMap.put(3, "test_user");
    }

    public static String mapUserType(int userTypeCode) {
        return userTypeMap.get(userTypeCode);
    }
}
