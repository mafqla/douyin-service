package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.CommentEntity;
import com.yali.vilivili.model.entity.VideosCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 分类管理
 *
 * @author fuqianlin
 * @date 2023-04-17 2:27
 **/
public interface CommentRepository extends JpaRepository<CommentEntity, String> {

    List<CommentEntity> findAllByVideoId(long videoId);

}
