package com.yali.vilivili.utils;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.*;
import org.springframework.http.HttpStatus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

        try{
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(inputFile);
            ff.start();
            //获取视频总帧数
            int ffLength = ff.getLengthInFrames();
            //获取视频帧率
            int ffFrameRate = (int) ff.getFrameRate();
            //获取视频总秒数
            int seconds = ffLength / ffFrameRate;
            //获取视频封面的帧数
            int i = 0;
            if (seconds > 1) {
                i = seconds / 2;
            }
            //获取视频封面
            Frame frame = ff.grabImage();
            //获取视频封面的BufferedImage对象
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bi = converter.getBufferedImage(frame);
            //将BufferedImage对象转换为输入流
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", os);
            byte[] bytes = os.toByteArray();
            out = new ByteArrayInputStream(bytes);
            ff.stop();

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
    public static String getVideoDuration(InputStream inputFile) {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile)) {
            grabber.start();
            //获取视频时长
            long duration = grabber.getLengthInTime() / 1000000;
            // HH:mm:ss
            long hours = duration / 3600;
            long minutes = (duration % 3600) / 60;
            long seconds = duration % 60;
            String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            grabber.stop();
            return time;

        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
            throw new MyException(HttpStatus.FAILED_DEPENDENCY.toString(), "视频时长获取失败"+e.getMessage());
        }
    }
}
