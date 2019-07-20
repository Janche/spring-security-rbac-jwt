package com.example.janche.web.config.security;

import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.dao.RoleMapper;
import com.example.janche.user.dao.UserMapper;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.LoginUserDTO;
import com.example.janche.user.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lirong
 * @Date 2019-7-14 22:46:54
 * @Desc 从数据库查询用户数据
 */
@Component("securityUserService")
public class SecurityUserService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuRightMapper menuRightMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO = userMapper.getRolesByUsername(username);
        // 默认用户ID为1的为管理员
        if (null != userDTO){
            if(1L == userDTO.getId()) {
                this.getAdminPermission(userDTO);
            }
            SecurityUser securityUser = new SecurityUser(LoginUserDTO.user2LoginUserDTO(userDTO));
            return securityUser;
        } else {
            throw new UsernameNotFoundException(username + " 用户不存在!");
        }
    }

    /**
     * 为管理员赋所有权限
     * @param userDTO
     * @return
     */
    private UserDTO getAdminPermission(UserDTO userDTO) {
        List<Role> roles = roleMapper.selectAll();
        List<MenuRight> menuRights = menuRightMapper.selectAll();
        userDTO.setRoles(roles);
        userDTO.setMenus(menuRights);
        return userDTO;
    }
}
