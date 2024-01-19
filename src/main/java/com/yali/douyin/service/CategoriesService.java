package com.yali.vilivili.service;


import com.yali.vilivili.model.entity.VideosCategoriesEntity;
import com.yali.vilivili.model.ro.AddCategoriesRO;
import com.yali.vilivili.model.vo.CategoriesVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoriesService {

    /**
     * 获取分类列表
     *
     * @return 分类列表
     */

    List<CategoriesVO> getCategoriesList();

    /**
     * 添加分类
     *
     * @param ro 分类信息
     */

    void addCategories(MultipartFile img, AddCategoriesRO ro) throws IOException;


    /**
     * 删除分类
     *
     * @param ro 分类id
     */
    void deleteById(Integer ro);


    /**
     * 更新分类信息
     *
     * @param ro 分类信息
     */
    void updateCategories(VideosCategoriesEntity ro);


    /**
     * 根据分类查询视频
     *
     * @param id 分类id
     */
    List<VideosCategoriesEntity> findCategoriesById(Integer id);
}
