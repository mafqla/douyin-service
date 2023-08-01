package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.AttentionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttentionRepository extends JpaRepository<AttentionEntity, Integer> {

    /**
     * 根据用户id和视频作者id查询关注信息
     *
     * @param userId   用户id
     * @param authorId 视频作者id
     */
    @Query(value = "select * from attention where user_id = ?1 and attention_id = ?2", nativeQuery = true)
    AttentionEntity findByUserIdAndAuthorId(int userId, int authorId);
}
