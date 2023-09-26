package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.AttentionEntity;
import com.yali.vilivili.model.vo.AttentionVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttentionRepository extends JpaRepository<AttentionEntity, Integer> {

    /**
     * 根据用户id和视频作者id查询关注信息
     *
     * @param userId   用户id
     * @param authorId 视频作者id
     */
    @Query(value = "select * from attention where user_id = ?1 and attention_id = ?2", nativeQuery = true)
    AttentionEntity findByUserIdAndAuthorId(int userId, int authorId);

    /**
     * 根据用户id查询关注列表
     *
     * @param userId 用户id
     */
    @Query(value = "select * from attention where user_id = ?1", nativeQuery = true)
    List< AttentionEntity>  findAllByUserId(int userId);

    /**
     * 根据用户id查询关注数量
     *
     * @param userId 用户id
     */
    @Query(value = "select count(*) from attention where user_id = ?1", nativeQuery = true)
    int countByUserId(int userId);

    /**
     * 根据用户id查询粉丝列表
     */
    @Query(value = "select * from attention where attention_id = ?1", nativeQuery = true)
    List< AttentionEntity>  findAllByAttentionId(int userId);

    /**
     * 根据用户id查询粉丝数量
     * @param userId 用户id
     */
    @Query(value = "select count(*) from attention where attention_id = ?1", nativeQuery = true)
    int countByAttentionId(int userId);
}
