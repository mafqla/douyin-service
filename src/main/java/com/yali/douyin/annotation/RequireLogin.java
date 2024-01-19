package com.yali.vilivili.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 登录注解，加在controller方法上，
 * 加了该注解并且required为true进行判断是否符合放行条件，
 * 如果不加或者为required为false则直接放行
 * @Date 2023/1/8 1:16
 * @Author pq
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireLogin {
    boolean required() default true;
}
