package com.yali.vilivili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.vilivili.mapper.*;
import com.yali.vilivili.model.entity.*;
import com.yali.vilivili.model.vo.VideosEntityVO;
import com.yali.vilivili.repository.VideosRepository;
import com.yali.vilivili.service.VideosService;
import com.yali.vilivili.utils.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视频接口
 *
 * @author fuqianlin
 * @date 2023-01-25 15:31
 **/

@Service
public class VideosServiceImpl extends ServiceImpl<VideosEntityMapper, VideosEntity> implements VideosService {
    @Resource
    private VideosRepository videosRepository;


    @Value("${server.port}")
    private String port;

    @Value("${file.upload.context-path}")
    private String contextPath;

    @Resource
    CommentMapper commentMapper;

    @Resource
    CollectionMapper collectionMapper;

    @Resource
    VideosInfoEntityMapper videosInfoEntityMapper;

    @Resource
    UserEntityMapper userEntityMapper;

    @Resource
    VideosTagMapper videosTagMapper;

    @Resource
    private  FileUploadServiceImpl fileUploadService;

    /**
     * 获取视频列表
     *
     * @return 视频列表
     */
    @Override
    public List<VideosEntity> getVideosList() {

        try {
            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
            List<VideosEntity> videosList = videosRepository.findAll();
            for (VideosEntity video : videosList) {
                if (!video.getVideosAddress().contains(url) && !video.getVideosCover().contains(url)) {
                    video.setVideosAddress(url + video.getVideosAddress());
                    video.setVideosCover(url + video.getVideosCover());
                }
            }
            return videosList;

        } catch (Exception e) {
            throw new MyException("获取视频列表失败", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    /**
     * 分页查询视频列表
     *
     * @param page   页码
     * @param size   每页数量
     * @param status 状态
     * @return videoList 视频列表
     */
    @Override
    public List<VideosEntity> getVideosListByPage(Integer page, Integer size, Integer status) {
        // 计算分页起始位置
        page = (page - 1) * size;
        try {
            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
            List<VideosEntity> videosList = videosRepository.findAllByPage(page, size, status);
            for (VideosEntity video : videosList) {
                if (!video.getVideosAddress().contains(url) && !video.getVideosCover().contains(url)) {
                    video.setVideosAddress(url + video.getVideosAddress());
                    video.setVideosCover(url + video.getVideosCover());
                }
            }
            if (videosList.size() == 0)
            //返回空数值的状态码
            {
                throw new MyException("没有更多数据了", String.valueOf(HttpStatus.NO_CONTENT.value()));
            }
            return videosList;

        }//返回空数值的状态码
        catch (MyException e) {
            throw new MyException(e.getMessage(), e.getCode());
        } catch (Exception e) {
            throw new MyException("获取视频列表失败", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @Resource
    VideosEntityMapper videosEntityMapper;


    /**
     * 视频滚动加载接口
     *
     * @param cursor 当前页码
     * @param size   每页数量
     */
    @Override
    public List<VideosEntityVO> getVideosListByCursor(Integer cursor, Integer size, Integer status) {

        try {
            // 获取视频列表（假设您有一个名为 videosRepository 的仓库对象，并且它有一个 findAllByPage 方法，该方法接受分页起始位置和每页数量参数，并返回 VideosEntity 对象的列表）
            List<VideosEntity> videosList = videosRepository.findVideosByCursor(cursor, size, status);

            // 格式化视频地址和封面地址
            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
            for (VideosEntity video : videosList) {
                if (!video.getVideosAddress().contains(url) && !video.getVideosCover().contains(url)) {
                    video.setVideosAddress(url + video.getVideosAddress());
                    video.setVideosCover(url + video.getVideosCover());
                }
            }

            if (videosList.size() == 0)
            //返回空数值的状态码
            {
                throw new MyException("没有更多数据了", String.valueOf(HttpStatus.NO_CONTENT.value()));
            }
            // 返回视频列表
            List<VideosEntityVO> videosEntityVOS = new ArrayList<>();
            videosList.forEach(videosEntity -> {
                QueryWrapper<CommentEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("video_id", videosEntity.getId());
                List<CommentEntity> commentEntities = commentMapper.selectList(queryWrapper);
                VideosEntityVO videosEntityVO = new VideosEntityVO();
                BeanUtils.copyProperties(videosEntity, videosEntityVO);

                if (commentEntities.size() != 0) {
                    List<String> commentInfoList = commentEntities.stream().map(CommentEntity::getCommentInfo).toList();
                    videosEntityVO.setCommentList(commentInfoList);
                    videosEntityVO.setCommentCount(commentInfoList.size());
                }
                QueryWrapper<CollectionEntity> collectionEntityQueryWrapper = new QueryWrapper<>();
                collectionEntityQueryWrapper.eq("video_id", videosEntity.getId());
                Long collectionCount = collectionMapper.selectCount(collectionEntityQueryWrapper);
                videosEntityVO.setCollectionCount(collectionCount);
                QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
                videosInfoEntityQueryWrapper.eq("video_id", videosEntity.getId());
                List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper);
                if (videosInfoEntities.size() != 0) {
                    long userId = videosInfoEntities.get(0).getUserId();
                    UserEntity user = userEntityMapper.selectById(userId);
                    String avatarUrl = fileUploadService.getImageUrl(user.getUserAvatar());
                    videosEntityVO.setAuthorAvatar(avatarUrl);
                    videosEntityVO.setAuthorName(user.getUsername());
                    List<Long> tagIdList = videosInfoEntities.stream().map(VideosInfoEntity::getTagId).toList();
                    List<String> tagNameList = videosTagMapper.selectBatchIds(tagIdList).stream().map(VideosTagEntity::getTagName).toList();
                    videosEntityVO.setTagNameList(tagNameList);
                }
                videosEntityVOS.add(videosEntityVO);
            });
            return videosEntityVOS;

        }//返回空数值的状态码
        catch (MyException e) {
            throw new MyException(e.getMessage(), e.getCode());
        } catch (Exception e) {
            throw new MyException("获取视频列表失败", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

}
