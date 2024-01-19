package com.yali.vilivili.repository;


import com.yali.vilivili.model.entity.VideosTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 标签管理
 *
 * @author fuqianlin
 * @date 2023-04-17 1:27
 **/
public interface TagRepository extends JpaRepository<VideosTagEntity, String> {
}
