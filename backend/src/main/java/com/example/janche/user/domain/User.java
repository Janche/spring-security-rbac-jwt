package com.example.janche.user.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "actual_name")
    private String actualName;

    /**
     * 性别: 0-男，1-女
     */
    private Integer sex;

    /**
     * email
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 职称
     */
    @Column(name = "post_name")
    private String postName;

    /**
     * 状态: 0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;
}