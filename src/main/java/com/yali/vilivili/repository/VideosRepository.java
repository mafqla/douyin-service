package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.VideosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fuqianlin
 * @date 2023-01-22 15:48
 **/
public interface VideosRepository extends JpaRepository<VideosEntity, String> {

}
