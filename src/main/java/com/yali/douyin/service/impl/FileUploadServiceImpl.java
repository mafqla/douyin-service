package com.yali.vilivili.service.impl;

import com.yali.vilivili.mapper.VideosInfoEntityMapper;
import com.yali.vilivili.mapper.VideosTagMapper;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.entity.VideosEntity;
import com.yali.vilivili.model.entity.VideosInfoEntity;
import com.yali.vilivili.model.entity.VideosTagEntity;
import com.yali.vilivili.model.ro.VideosRo;
import com.yali.vilivili.model.vo.FileUploadVO;
import com.yali.vilivili.repository.VideosRepository;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.utils.FFmpegUtils;
import com.yali.vilivili.utils.HostHolder;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.Date;
import java.util.UUID;


/**
 * 文件上传
 *
 * @author fuqianlin
 * @date 2023-01-18 21:56
 **/

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {


    @Resource
    private VideosRepository videosRepository;

    @Resource
    VideosTagMapper videosTagMapper;


    @Value("${file.upload.path}")
    private String filePath;
    @Value("${file.upload.image.suffix}")
    private String imageSuffix;
    @Value("${server.port}")
    private String port;

    @Value("${file.upload.context-path}")
    private String contextPath;
    @Value("${file.upload.video.suffix}")
    private String videoSuffix;
    @Value("${file.upload.video.path}")
    private String videoPath;
    @Value("${file.upload.video.cover-path}")
    private String coverPath;

    @Value("${file.upload.icon.path}")
    private String iconPath;

    @Override
    public FileUploadVO imageUpload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，请选择文件");
        }
        // 转换类型

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (!imageSuffix.contains(fileName.substring(fileName.lastIndexOf(".") + 1))) {
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，文件格式不正确");
        }
        // 文件上传后的路径,文件重命名，防止重复
        fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        File dest = new File(filePath + fileName);
        try {
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            // 文件写入
            FileUtils.copyInputStreamToFile(file.getInputStream(), dest);

            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/" + fileName;
            // 去除src/main/resources前缀,返回相对路径
            String path = filePath.substring(18) + fileName;
            log.info("图片上传成功，图片地址为：" + path + " url:" + url);
            FileUploadVO fileUploadVO = new FileUploadVO();
            fileUploadVO.setUrl(url);
            fileUploadVO.setPath(path);
            return fileUploadVO;
        } catch (IOException e) {
            log.error("上传失败", e);
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，文件写入失败");
        }
    }

    /**
     * 图片上传
     *
     * @param file    文件
     * @param urlPath 图片链接
     */
    @Override
    public FileUploadVO urlUpload(MultipartFile file, String urlPath) throws IOException {
        // 判断文件和路径是否为空
        if (file == null && StringUtils.isBlank(urlPath)) {
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，请选择文件或者输入链接");
        }

        String fileName = "";
        InputStream inputStream = null;
        boolean fromUrl = false;

        if (file != null) {
            // 从文件中获取文件名和流
            fileName = file.getOriginalFilename();
            assert fileName != null;
            if (!imageSuffix.contains(fileName.substring(fileName.lastIndexOf(".") + 1))) {
                throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，文件格式不正确");
            }
            inputStream = file.getInputStream();
        } else {
            // 从URL中下载并获取文件名和流
            fileName = UUID.randomUUID() + urlPath.substring(urlPath.lastIndexOf("."));
            try {
                inputStream = new URL(urlPath).openStream();
                fromUrl = true;
            } catch (IOException e) {
                log.error("下载失败", e);
                throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "下载失败");
            }
        }

        // 上传文件
        fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
        File dest = new File(iconPath + fileName);
        try {
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            // 文件写入
            FileUtils.copyInputStreamToFile(inputStream, dest);
            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/" + fileName;
            String path = iconPath.substring(18) + fileName;
            if (fromUrl) {
                log.info("图片下载成功，图片地址为：" + path);
            } else {
                log.info("图片上传成功，图片地址为：" + path);
            }
            FileUploadVO fileUploadVO = new FileUploadVO();
            fileUploadVO.setUrl(url);
            fileUploadVO.setPath(path);
            return fileUploadVO;
        } catch (IOException e) {
            log.error("上传失败", e);
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，文件写入失败");
        }
    }

    /**
     * 根据路径返回图片在线预览地址
     *
     * @param path 路径
     * @return url
     */
    @Override
    public String getImageUrl(String path) {

        try {
            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath;
            if (path.startsWith("/static/avatar/")) {
                //去除掉/static/avatar/，只留下文件名
                path = path.substring(14);
                return url + path;
            } else if (path.startsWith("/static/default_logo/")) {
                //去除掉/static/default_logo/，只留下文件名
                path = path.substring(20);
                return url + path;
            } else if (path.startsWith("/static/icon/")) {
                path = path.substring(12);
                return url + path;
            } else {
                return path;
            }
        } catch (Exception e) {
            log.error("获取图片地址失败", e);
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "获取图片地址失败");
        }
    }

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private VideosInfoEntityMapper videosInfoEntityMapper;

    /**
     * 视频上传
     *
     * @param videosRo 视频
     * @return url and path
     */
    @Override
    public Void videoUpload(MultipartFile videos, VideosRo videosRo) {
        if (videos.isEmpty()) {
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，请选择文件");
        }
        String fileName = videos.getOriginalFilename();
        System.out.println(fileName);
        assert fileName != null;
        if (!videoSuffix.contains(fileName.substring(fileName.lastIndexOf(".") + 1))) {
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "上传失败，文件格式不正确");
        }
        // 文件上传后的路径,文件重命名，防止重复
        fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));

        //将视频转换为mp4格式

        try {
            // 获取视频时长
            String duration = FFmpegUtils.getVideoDuration(videos.getInputStream());
            System.out.println(duration);

            String coverName = String.valueOf(UUID.randomUUID());
            //图片保存为png格式
            coverName = coverName + ".png";
            //视频保存，如果为mp4格式，直接保存，否则转换为mp4格式
            if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("mp4")) {
                FileUtils.copyInputStreamToFile(videos.getInputStream(),
                        new File(videoPath + fileName));
                // 视频路径
                String path = videoPath + fileName;

                FileUtils.copyInputStreamToFile(FFmpegUtils.getVideoCover(path),
                        new File(coverPath + coverName));


            } else {
                InputStream convertToMp4 = FFmpegUtils.convertToMp4(videos.getInputStream());
                //转换为mp4格式
                fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".mp4";
                FileUtils.copyInputStreamToFile(convertToMp4,
                        new File(videoPath + fileName));
                // 视频路径
                String path = videoPath + fileName;
                FileUtils.copyInputStreamToFile(FFmpegUtils.getVideoCover(path),
                        new File(coverPath + coverName));
            }
            // 视频封面路径去除掉src/main/resources前缀,返回相对路径
            String coverPathFormat = coverName;
            // 视频路径去除掉src/main/resources前缀,返回相对路径
            String videoPathFormat = fileName;
            //保存视频信息
            VideosEntity videosEntity = new VideosEntity();
            videosEntity.setTitle(videosRo.getTitle());
            videosEntity.setDescription(videosRo.getDescription());
            videosEntity.setVideosCover(coverPathFormat);
            videosEntity.setVideosAddress(videoPathFormat);
            videosEntity.setVideosTime(duration);
//            videosEntity.setUsername(videosRo.getUsername());
            videosEntity.setStatus(videosEntity.getStatus());
            videosEntity.setUploadTime(new Date());

            videosRepository.save(videosEntity);

            UserEntity user = hostHolder.get();
            VideosInfoEntity videosInfoEntity = new VideosInfoEntity();
            videosInfoEntity.setVideoId(videosEntity.getId());
            videosInfoEntity.setUserId(user.getId());


            //保存视频标签
            VideosTagEntity videosTagEntity=new VideosTagEntity();
            videosTagEntity.setTagName(videosRo.getTagName());
            videosTagEntity.setCreateTime(new Date());
            videosTagEntity.setUpdateTime(new Date());
            videosTagMapper.insert(videosTagEntity);
            videosInfoEntity.setTagId(videosTagEntity.getId());
            videosInfoEntityMapper.insert(videosInfoEntity);

        } catch (Exception e) {
            log.error("服务器错误", e);
            throw new MyException(String.valueOf(HttpStatus.FAILED_DEPENDENCY.value()), "服务器错误");
        }
        return null;
    }


}
