package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.VideosCategoriesEntity;
import com.yali.vilivili.model.ro.AddCategoriesRO;
import com.yali.vilivili.model.vo.CategoriesVO;
import com.yali.vilivili.repository.CategoriesRepository;
import com.yali.vilivili.service.CategoriesService;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.utils.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 分类
 *
 * @author fuqianlin
 * @date 2023-04-17 00:26
 **/

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Resource
    private FileUploadService fileUploadService;
    @Resource
    private CategoriesRepository categoriesRepository;

    /**
     * 添加分类
     *
     * @param ro 分类信息
     */
    @Override
    public void addCategories(MultipartFile img, AddCategoriesRO ro) throws IOException {
        // 查询数据库中是否已经存在同名分类
        Optional<VideosCategoriesEntity> categoriesOptional = categoriesRepository.findByCategoriesName(ro.getCategoriesName());
        if (categoriesOptional.isPresent()) {
            throw new MyException("204", "分类已存在");
        }
        String imgUrl = String.valueOf(fileUploadService.urlUpload(img, ro.getImg()).getPath());
        VideosCategoriesEntity categoriesEntity = new VideosCategoriesEntity();
        categoriesEntity.setDescription(ro.getDescription());
        categoriesEntity.setImg(imgUrl);
        categoriesEntity.setCategoriesName(ro.getCategoriesName());
        categoriesEntity.setCreateTime(new Date());
        categoriesEntity.setUpdateTime(new Date());

        categoriesRepository.save(categoriesEntity);


    }

    /**
     * 获取分类列表
     *
     * @return 分类列表
     */
    @Override
    public List<CategoriesVO> getCategoriesList() {

        try {
            List<VideosCategoriesEntity> categoriesEntities = categoriesRepository.findAll();
            //处理图片路径
            for (VideosCategoriesEntity categoriesEntity : categoriesEntities) {
                categoriesEntity.setImg(fileUploadService.getImageUrl(categoriesEntity.getImg()));
            }

            // 处理图片路径
            for (VideosCategoriesEntity categoriesEntity : categoriesEntities) {
                categoriesEntity.setImg(fileUploadService.getImageUrl(categoriesEntity.getImg()));
            }

            // 转换为 CategoriesVO 对象
            List<CategoriesVO> categoriesVOs = new ArrayList<>();
            for (VideosCategoriesEntity categoriesEntity : categoriesEntities) {
                CategoriesVO categoriesVO = new CategoriesVO();
                categoriesVO.setId(categoriesEntity.getId());
                categoriesVO.setImg(categoriesEntity.getImg());
                categoriesVO.setTitle(categoriesEntity.getCategoriesName());
                categoriesVO.setDescription(categoriesEntity.getDescription());
                categoriesVOs.add(categoriesVO);
            }

            return categoriesVOs;
        } catch (Exception e) {
            throw new MyException("204", "获取分类列表失败");
        }
    }


    /**
     * 删除分类
     *
     * @param ro 分类id
     */
    @Override
    public void deleteById(Integer ro) {

    }

    /**
     * 更新分类信息
     *
     * @param ro 分类信息
     */
    @Override
    public void updateCategories(VideosCategoriesEntity ro) {

    }

    /**
     * 根据分类查询视频
     *
     * @param id 分类id
     */
    @Override
    public List<VideosCategoriesEntity> findCategoriesById(Integer id) {
        return null;
    }
}
