package com.yali.vilivili.model.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


/**
 * 用户信息表
 *
 * @author fuqianlin
 * @date 2023-04-16 13:21
 **/

@Data
@Entity
@Table(name = "user_info")
@DynamicInsert
@DynamicUpdate
public class UserInfoEntity {

    @Id
    @Column(name = "user_id")
    private Integer userId; // 用户 ID

    @Column(name = "user_num")
    private String userNum; // 用户编号

    @Column(name = "gender")
    private String gender; // 用户性别

    @Column(name = "birthdate")
    private String birthdate; // 用户生日

    @Column(name = "signature")
    private String signature; // 用户签名

    @Column(name = "school")
    private String school; // 用户学校

    @Column(name = "location")
    private String location; // 用户所在地

    //关联user表
    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

}
