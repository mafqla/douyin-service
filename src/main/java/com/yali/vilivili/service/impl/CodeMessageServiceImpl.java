package com.yali.vilivili.service.impl;

import com.yali.vilivili.service.CodeMessageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description 读取错误信息配置文件
 * @Date 2022/12/22 12:24
 * @Author pq
 */
@Service
@Slf4j
public class CodeMessageServiceImpl implements CodeMessageService {

    private Properties errorCodeProperties;

    @PostConstruct
    public void init() {
        InputStream is = null;
        try {
            errorCodeProperties = new Properties();
            is = this.getClass().getResourceAsStream("/codeMessage.properties");
            errorCodeProperties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("code message init fail");
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String message(String code) {
        return errorCodeProperties.getProperty(code);
    }
}
