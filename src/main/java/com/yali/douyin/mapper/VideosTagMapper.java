package com.yali.vilivili.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yali.vilivili.model.entity.VideosInfoEntity;
import com.yali.vilivili.model.entity.VideosTagEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface VideosTagMapper extends BaseMapper<VideosTagEntity> {
}
