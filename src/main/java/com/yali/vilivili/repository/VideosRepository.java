package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.VideosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author fuqianlin
 * @date 2023-01-22 15:48
 **/
public interface VideosRepository extends JpaRepository<VideosEntity, String> {


    /**
     * 分页查询视频列表
     *
     * @param page   页码
     * @param size   每页数量
     * @param status 状态
     */
    @Query(value = "select * from videos where status = ?3  limit ?1,?2", nativeQuery = true)
    List<VideosEntity> findAllByPage(Integer page, Integer size, Integer status);
}
