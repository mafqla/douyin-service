package com.yali.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.yali.common.constants.ErrorInfo;
import com.yali.common.exceptions.BadRequestException;
import lombok.Getter;

@Getter
public enum UserType implements BaseEnum {
    NORMAL(0, "普通用户"),
    ACTOR(1, "演员"),
    MUSICIAN(2, "音乐创作人"),
    ORGANIZATION(3, "官方机构"),
    SHOP(4, "店铺"),
    COMPANY(5, "公司"),
    ;
    @EnumValue
    int value;
    String desc;

    UserType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UserType of(int value) {
        for (UserType type : UserType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new BadRequestException(ErrorInfo.Msg.INVALID_USER_TYPE);
    }
}
