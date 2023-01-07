package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Description
 * @Date 2023/1/8 2:55
 * @Author pq
 */
@Data
public class AESRO {

    @ApiModelProperty("明文")
    private String plaintext;

    @ApiModelProperty("密文")
    private String ciphertext;
}
