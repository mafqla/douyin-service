package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.VideosCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 分类管理
 *
 * @author fuqianlin
 * @date 2023-04-17 2:27
 **/
public interface CategoriesRepository extends JpaRepository<VideosCategoriesEntity, String> {
    Optional<VideosCategoriesEntity> findByCategoriesName(String categoriesName);
}
