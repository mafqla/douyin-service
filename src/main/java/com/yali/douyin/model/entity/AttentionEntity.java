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
@Table(name = "attention")
@DynamicInsert
@DynamicUpdate
@TableName(value = "attention")
public class AttentionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long Id;


    // 关注的用户id
    @Column(name = "attention_id")
    private long attentionId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "attention_time")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date attentionTime;
}
