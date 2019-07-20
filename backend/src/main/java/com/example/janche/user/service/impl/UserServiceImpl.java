package com.example.janche.user.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.user.dao.UserMapper;
import com.example.janche.user.domain.User;
import com.example.janche.user.service.UserService;
import com.example.janche.common.restResult.PageParam;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
* @author lirong
* @Description:
* @date 2019-07-20 04:26:39
*/
@Slf4j
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    @Override
    public List<User> list(PageParam pageParam, String query) {
        Example example = new Example(User.class);
        //TODO 设置查询字段
        //example.or().andLike("name", "%"+query+"%");
        //example.or().andLike("code", "%"+query+"%");

        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return userMapper.selectByExample(example);
    }
}
