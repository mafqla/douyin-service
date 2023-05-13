package com.yali.vilivili.utils;

import com.yali.vilivili.model.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * @author yujie
 * @createTime 2023/5/13 16:04
 * @description
 */
@Component
public class HostHolder {

    private static ThreadLocal<UserEntity> threadLocal = new ThreadLocal<>();

    public void setUser(UserEntity user){
        threadLocal.set(user);
    }

    public UserEntity get(){
        return threadLocal.get();
    }

    public void clear(){
        threadLocal.remove();
    }

}
