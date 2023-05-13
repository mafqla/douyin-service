package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.LikeEntity;
import com.yali.vilivili.model.entity.VideosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author fuqianlin
 * @date 2023-01-22 15:48
 **/
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {


}
