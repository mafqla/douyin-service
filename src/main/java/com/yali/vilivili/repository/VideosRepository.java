package com.yali.vilivili.repository;

import com.yali.vilivili.model.entity.VideosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author fuqianlin
 * @date 2023-01-22 15:48
 **/
public interface VideosRepository extends JpaRepository<VideosEntity, Long> {


    VideosEntity findAllById(Long id);


    /**
     * 分页查询视频列表
     *
     * @param page   页码
     * @param size   每页数量
     * @param status 状态
     */
    @Query(value = "select * from videos where status = ?3  limit ?1,?2", nativeQuery = true)
    List<VideosEntity> findAllByPage(Integer page, Integer size, Integer status);

    /**
     * 视频滚动加载接口
     *
     * @param cursor 当前页码
     * @param size   每页数量
     * @param status 状态
     */
    @Query(value = "SELECT * FROM videos WHERE id > ?1 AND (?3 IS NULL OR status = ?3) ORDER BY id LIMIT ?2", nativeQuery = true)
    List<VideosEntity> findVideosByCursor(Integer cursor, Integer size, Integer status);

}
