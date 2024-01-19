package com.yali.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yali.message.domain.po.NoticeTask;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 系统通告的任务表，可以延期或定期发送通告 Mapper 接口
 * </p>
 */
public interface NoticeTaskMapper extends BaseMapper<NoticeTask> {

    @Select("SELECT user_id FROM notice_task_target WHERE task_id = #{task_id}")
    List<Long> queryTaskTargetByTaskId(Long taskId);
}
