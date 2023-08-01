package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.AttentionEntity;
import com.yali.vilivili.repository.AttentionRepository;
import com.yali.vilivili.service.AttentionService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 关注
 *
 * @author fuqianlin
 * @date 2023-07-31 23:27
 **/

@Service
@Slf4j
public class AttentionServiceImpl implements AttentionService {

    @Resource
    private AttentionRepository attentionRepository;

    /**
     * 查询用户是否关注视频作者
     *
     * @param userId   用户id
     * @param authorId 视频作者id
     */
    @Override
    public Boolean isAttention(int userId, int authorId) {
        try {
            AttentionEntity isAttention = attentionRepository.findByUserIdAndAuthorId(userId, authorId);

            return isAttention != null;
        } catch (Exception e) {
            log.error("查询用户是否关注视频作者失败", e);
            throw new MyException("500", "查询用户是否关注视频作者失败");
        }

    }
}
