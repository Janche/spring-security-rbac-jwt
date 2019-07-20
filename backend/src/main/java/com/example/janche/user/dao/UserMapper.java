package com.example.janche.user.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends Mapper<User> {
    UserDTO getRolesByUsername(@Param("username") String username);
}