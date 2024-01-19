package com.yali.vilivili.model.ro;

import lombok.Data;

/**
 * 删除或禁用用户
 *
 * @author fuqianlin
 * @date 2023-01-06 22:46
 **/

@Data
public class deleteByUserIdRO {
    /**
     * 用户id
     */


    private int id;
    /**
     * 是否禁用
     */
    private byte isValid;
}
