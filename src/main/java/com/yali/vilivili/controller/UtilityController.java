package com.yali.vilivili.controller;

import com.yali.vilivili.annotation.RequireLogin;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.ro.AESRO;
import com.yali.vilivili.utils.AESUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Date 2023/1/8 2:39
 * @Author pq
 */
@RestController
@Api(value = "工具", tags = {"工具"})
@RequestMapping("/util")
public class UtilityController extends BaseController {

    @ApiOperation(value = "AES加密")
    @PostMapping("/AESEncrypt")
    @RequireLogin(required = false)
    public ResponseEntity<OR<String>> aESEncrypt(AESRO ro) {
        return processData(() -> AESUtil.encrypt(ro.getPlaintext()), this::processException);
    }

    @ApiOperation(value = "AES解密")
    @PostMapping("/AESDecrypt")
    @RequireLogin(required = false)
    public ResponseEntity<OR<String>> aESDecrypt(AESRO ro) {
        return processData(() -> AESUtil.decrypt(ro.getCiphertext()), this::processException);
    }
}
