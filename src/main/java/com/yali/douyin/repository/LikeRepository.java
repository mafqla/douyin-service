package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author fuqianlin
 * @date 2023-01-22 15:48
 **/
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {


    /**
     * 根据用户id分页查询点赞
     *
     * @param userId 用户id
     * @param page   页码
     * @param size   每页数量
     * @return 点赞
     */
    @Query(value = "select * from applaud where user_id = ?1 order by like_time desc limit ?2,?3", nativeQuery = true)
    List<LikeEntity> findAllByUserIdOrderByCreateTimeDesc(int userId, Integer page, Integer size);

    /**
     * 根据用户id查询当前用户点赞的视频数量
     *
     * @param userId 用户id
     * @return 点赞
     */
    @Query(value = "select count(*) from applaud where user_id = ?1", nativeQuery = true)
    long countbyUserId(int userId);

    /**
     * 根据用户id和视频id查询点赞
     *
     * @param userId  用户id
     * @param videoId 视频id
     * @return 点赞
     */
    @Query(value = "select * from applaud where user_id = ?1 and video_id = ?2", nativeQuery = true)
    LikeEntity findAllByUserIdAndVideoId(int userId, Long videoId);
}