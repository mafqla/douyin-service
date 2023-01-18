package com.yali.vilivili;

import com.yali.vilivili.service.FileUploadService;
import eu.bitwalker.useragentutils.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description
 * @Date 2023/1/19 0:06
 * @Author pq
 */
@SpringBootTest
public class FileUploadTest {

    @Resource
    private FileUploadService fileUploadService;

    @Test
    void uploadImageTest() {
        File file=new File("C:\\Users\\DBC20221101\\Desktop\\Snipaste_2023-01-19_00-12-26.png");
        System.out.println(fileUploadService.imageUpload(fileToMultipartFile(file)));
    }

    public static MultipartFile fileToMultipartFile(File file) {
        MultipartFile result = null;
        if (null != file) {
            try (FileInputStream input = new FileInputStream(file)) {
                result = new MockMultipartFile(file.getName().concat("temp"), file.getName(), "text/plain", input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
