package com.yali.vilivili.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;
import org.springframework.http.HttpStatus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 视频转换工具类
 * @author fuqianlin
 * @date 2023-01-22 18:58
 */
@Slf4j
public class FFmpegUtils {


    /**
     * 视频转换为mp4格式
     *
     * @param inputFile 输入文件
     * @return in 输出输入流
     */
    public static ByteArrayInputStream convertToMp4(InputStream inputFile) {
        ByteArrayInputStream inputStream;
        try {
            //使用ffmpeg将输入流转换为mp4格式
            FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(inputFile);
            frameGrabber.start();
            //把inputFile转换为
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //设置输出格式
            FrameRecorder recorder = new FFmpegFrameRecorder(out,
                    frameGrabber.getImageWidth(), frameGrabber.getImageHeight(), frameGrabber.getAudioChannels());
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("matroska");
            recorder.setFrameRate(frameGrabber.getFrameRate());
            recorder.setSampleRate(frameGrabber.getSampleRate());
            recorder.setVideoOption("preset", "ultrafast");
            recorder.start();
            Frame frame;
            while ((frame = frameGrabber.grab()) != null) {
                recorder.record(frame);
            }
            frameGrabber.stop();
            recorder.stop();
            byte[] bytes = out.toByteArray();
            inputStream = new ByteArrayInputStream(bytes);


        } catch (FrameGrabber.Exception e) {
            log.error("视频转码失败", e);
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "服务器异常");
        } catch (FrameRecorder.Exception e) {
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), e.getMessage());
        }
        return  inputStream;
    }




    /**
     * 获取视频封面
     *
     * @param inputFile 视频文件
     * @return outputFile 封面文件
     */
    public static ByteArrayInputStream getVideoCover(InputStream inputFile) {
        ByteArrayInputStream out = null;

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile)) {
            grabber.start();
            grabber.setFormat("mp4");
            // 获取视频总帧数
            int length = grabber.getLengthInFrames();
            // 获取视频帧率
            double frameRate = grabber.getFrameRate();
            // 获取视频封面帧
            int i = 0;
            Frame frame = null;
            while (i < length) {
                // 过滤前5秒和最后5秒的帧
                if (i > frameRate * 5 && i < length - frameRate * 5) {
                    frame = grabber.grabImage();
                    if (frame != null) {
                        break;
                    }
                }
                i++;
            }
            // 获取视频封面
            if (frame != null) {
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bufferedImage = converter.getBufferedImage(frame);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", baos);
                grabber.stop();
                byte[] bytes = baos.toByteArray();
                out = new ByteArrayInputStream(bytes);
            }


        } catch (IOException e) {
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "视频封面获取失败"+e.getMessage());
        }
        return out;
    }

    /**
     * 获取视频时长
     * @param inputFile 视频文件
     * @return HH:mm:ss
     */
    public static int getVideoDuration(File inputFile) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile)) {
            grabber.start();
            int duration = (int) grabber.getLengthInTime() / (1000 * 1000);
            grabber.stop();
            return duration;
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
