package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.VideosInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 */
public interface VideosInfoRepository extends JpaRepository<VideosInfoEntity, Integer> {

    /**
     * 根据视频id查询用户id
     */
    @Query(value = "select user_id from videos_info where video_id = ?1", nativeQuery = true)
    Integer findAllByVideoId(Long videoId);

    /**
     * 根据用户id分页查询视频信息列表
     *
     * @param userId 用户id
     * @param page   页码
     * @param size   每页数量
     */

    @Query(value = "select * from videos_info where user_id = ?1 order by video_id desc limit ?2,?3", nativeQuery = true)
    List<VideosInfoEntity> findAllByUserId(int userId, Integer page, Integer size);

    /**
     * 根据用户id查询视频信息数量
     *
     * @param userId 用户id
     * @return 视频信息数量
     */
    @Query(value = "select count(*) from videos_info where user_id = ?1", nativeQuery = true)
    long countbyUserId(int userId);
}
