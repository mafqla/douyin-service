package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.AttentionEntity;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.vo.AttentionVO;
import com.yali.vilivili.repository.AttentionRepository;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.AttentionService;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * @return 是否关注 0-自己 1-已关注 2-未关注 3-互相关注
     */
    @Override
    public Integer isAttention(int userId, int authorId) {
        try {
            if (userId == authorId) {
                return 0;
            }
            // 检查用户是否关注作者
            AttentionEntity userToAuthor = attentionRepository.findByUserIdAndAuthorId(userId, authorId);

            // 检查作者是否关注用户
            AttentionEntity authorToUser = attentionRepository.findByUserIdAndAuthorId(authorId, userId);
            if (userToAuthor != null && authorToUser != null) {
                return 3; // 互相关注
            }
            if (userToAuthor == null) {
                return 2; // 未关注
            } else {
                return 1; // 单向关注
            }

        } catch (Exception e) {
            log.error("查询用户是否关注视频作者失败", e);
            throw new MyException("500", "查询用户是否关注视频作者失败");
        }

    }

    /**
     * 查询当前用户关注列表
     *
     * @param userId 用户id
     */

    @Resource
    private UserRepository userRepository;
    @Resource
    private FileUploadService fileUploadService;

    @Override
    public List<AttentionVO> findAttentionList(Integer userId) {
        try {

            List<AttentionEntity> attention = attentionRepository.findAllByUserId(userId);
            List<AttentionVO> attentionVOList = new ArrayList<>();
            for (AttentionEntity attentionEntity : attention) {
                AttentionVO attentionVO = new AttentionVO();
                UserEntity author = userRepository.findById((int) attentionEntity.getAttentionId());
                String avatarUrl = fileUploadService.getImageUrl(author.getUserAvatar());
                attentionVO.setId(attentionEntity.getAttentionId());
                attentionVO.setSignature(author.getSignature());
                attentionVO.setNickname(author.getUsername());
                attentionVO.setUserAuth(author.getUserAuth());
                attentionVO.setUserAuthType(author.getUserAuthType());
                attentionVO.setAvatar(avatarUrl);
                attentionVOList.add(attentionVO);
            }

            return attentionVOList;
        } catch (Exception e) {
            log.error("查询当前用户关注列表失败", e);
            throw new MyException("203", "查询当前用户关注列表失败");
        }
    }

    /**
     * 查询当前用户粉丝列表
     *
     * @param userId 用户id
     */
    @Override
    public List<AttentionVO> findFansList(Integer userId) {
        try {
            List<AttentionEntity> fans = attentionRepository.findAllByAttentionId(userId);
            List<AttentionVO> attentionVOList = new ArrayList<>();
            for (AttentionEntity attentionEntity : fans) {
                AttentionVO attentionVO = new AttentionVO();
                UserEntity author = userRepository.findById((int) attentionEntity.getUserId());
                String avatarUrl = fileUploadService.getImageUrl(author.getUserAvatar());
                attentionVO.setId(attentionEntity.getUserId());
                attentionVO.setUserAuth(author.getUserAuth());
                attentionVO.setUserAuthType(author.getUserAuthType());
                attentionVO.setSignature(author.getSignature());
                attentionVO.setNickname(author.getUsername());
                attentionVO.setAvatar(avatarUrl);
                attentionVOList.add(attentionVO);
            }

            return attentionVOList;
        } catch (Exception e) {
            log.error("查询当前用户粉丝列表失败", e);
            throw new MyException("203", "查询当前用户粉丝列表失败");
        }
    }

    /**
     * 查询当前用户关注数量
     *
     * @param userId 用户id
     */
    @Override
    public int findAttentionCount(Integer userId) {
        try {
            return attentionRepository.countByUserId(userId);
        } catch (Exception e) {
            log.error("查询当前用户关注数量失败", e);
            throw new MyException("203", "查询当前用户关注数量失败");
        }
    }

    /**
     * 查询当前用户粉丝数量
     *
     * @param userId 用户id
     */
    @Override
    public int findFansCount(Integer userId) {
        try {
            // 假设你有一个名为 attentionRepository 的关注信息存储库，可以调用它来查询粉丝数量
            return attentionRepository.countByAttentionId(userId);
        } catch (Exception e) {
            log.error("查询粉丝数量失败", e);
            throw new MyException("203", "查询粉丝数量失败");
        }
    }

    /**
     * 添加关注 / 取消关注
     *
     * @param userId      用户id
     * @param attentionId 关注的用户id
     */
    @Override
    public void addAttention(int userId, int attentionId) {
        try {
            if (attentionId == userId) {
                throw new MyException("203", "自己不能关注自己");
            }
            // 假设你有一个名为 attentionRepository 的关注信息存储库，可以调用它来添加关注 / 取消关注
            AttentionEntity attentionEntity = attentionRepository.findByUserIdAndAuthorId(userId, attentionId);
            if (attentionEntity == null) {
                attentionEntity = new AttentionEntity();
                attentionEntity.setUserId(userId);
                attentionEntity.setAttentionId(attentionId);
                attentionEntity.setAttentionTime(new Date());
                attentionRepository.save(attentionEntity);

                throw new MyException("200", "关注成功");
            } else {
                attentionRepository.delete(attentionEntity);
                throw new MyException("200", "取消关注成功");
            }
        } catch (MyException e) {
            throw new MyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("添加关注 / 取消关注失败", e);
            throw new MyException("203", "添加关注 / 取消关注失败");
        }
    }
}
