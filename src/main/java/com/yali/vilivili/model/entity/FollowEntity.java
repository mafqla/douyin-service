package com.yali.vilivili.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fuqianlin
 * @date 2023-01-09 18:25
 **/

@Data
@Entity
@Table(name = "follow")
@DynamicInsert
@DynamicUpdate
@TableName(value="follow")
public class FollowEntity {
    @Id
    @Column(name = "follower_id")
    private long followerId;


    @Column(name = "followee_id")
    private long followeeId;

    @Column(name = "follow_time")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date followTime;
}
