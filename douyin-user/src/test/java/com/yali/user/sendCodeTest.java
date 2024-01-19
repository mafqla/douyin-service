package com.yali.user;

import com.yali.user.service.ICodeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author fuqianlin
 * @date 2023-11-22 16:09
 **/
@SpringBootTest
public class sendCodeTest {
    @Resource
    private ICodeService codeService;

    @Test
    void sendCode() {
        codeService.sendVerifyCode("13901517624");
    }
}
