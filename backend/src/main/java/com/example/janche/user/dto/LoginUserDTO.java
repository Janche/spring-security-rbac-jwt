package com.example.janche.user.dto;


import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginUserDTO implements Serializable {
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
    private String postName;

    /**
     * 状态: 0-禁用，1-启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 角色列表
     */
    private List<Role> roles;

    /**
     * 权限菜单
     */
    private List<MenuRight> menus;


    public static LoginUserDTO user2LoginUserDTO(UserDTO userDTO) {
        return LoginUserDTO.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .actualName(userDTO.getActualName())
                .sex(userDTO.getSex())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .address(userDTO.getAddress())
                .postName(userDTO.getPostName())
                .status(userDTO.getStatus())
                .createTime(userDTO.getCreateTime())
                .modifyTime(userDTO.getModifyTime())
                .roles(userDTO.getRoles())
                .menus(userDTO.getMenus())
                .build();
    }

}
