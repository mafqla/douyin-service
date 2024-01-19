package com.yali.auth.domain.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 登录信息记录表
 * </p>
 *
 * @author 虎哥
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("login_record")
public class LoginRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户id
     */
    private String cellPhone;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登出时间
     */
    private LocalDateTime logoutTime;

    /**
     * 登录日期
     */
    private LocalDate loginDate;

    /**
     * 登录时长，单位是秒
     */
    private Long duration;

    /**
     * ip地址
     */
    private String ipv4;

    /**
     * 登录设备
     */
    private String device;

    /**
     * 登录浏览器
     */
    private String browser;

    /**
     * 状态：0-失败 1-成功
     */
    private Integer status;

    /**
     * 登录失败原因
     */
    private String failureReason;


    //给状态添加枚举值
    @Getter
    public enum Status {
        SUCCESS(1, "成功"), FAILURE(0, "失败");

        @EnumValue
        final int value;
        final String desc;

        Status(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }


}
