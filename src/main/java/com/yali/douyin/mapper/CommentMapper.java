package com.yali.vilivili.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yali.vilivili.model.entity.CommentEntity;
import com.yali.vilivili.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<CommentEntity> {

}
