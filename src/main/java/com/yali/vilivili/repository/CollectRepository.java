package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

/**
 * 收藏
 *
 * @author fuqianlin
 * @date 2023-07-26 14:00
 **/
public interface CollectRepository extends JpaRepository<CollectionEntity, String> {

    /**
     * 根据用户id分页查询收藏
     *
     * @param user_id 用户id
     * @param page    页码
     * @param size    每页数量
     * @return 收藏
     */
    @Query(value = "select * from collection where user_id = ?1 order by create_time desc limit ?2, ?3", nativeQuery = true)
    List<CollectionEntity> findByUserId(int user_id, Integer page, Integer size);

    /**
     * 根据用户id和视频id查询收藏
     *
     * @param user_id  用户id
     * @param video_id 视频id
     * @return 收藏
     */
    @Query(value = "select * from collection where user_id = ?1 and video_id = ?2", nativeQuery = true)
    List<CollectionEntity> findByUserIdAndVideoId(int user_id, long video_id);

    /**
     * 根据用户id和视频id删除收藏
     *
     * @param user_id  用户id
     * @param video_id 视频id
     */
    @Modifying
    @Query(value = "delete from collection where user_id = ?1 and video_id = ?2", nativeQuery = true)
    void deleteById(int user_id, int video_id);

    /**
     * 根据用户id查询收藏数量
     *
     * @param userId 用户id
     * @return 收藏数量
     */
    @Query(value = "select count(*) from collection where user_id = ?1", nativeQuery = true)
    long countbyUserId(int userId);


    /**
     * 根据视频id查询收藏数量
     * @param videoId 视频id
     */
    @Query(value = "select count(*) from collection where video_id = ?1", nativeQuery = true)
    long countbyVideoId(long videoId);

}
