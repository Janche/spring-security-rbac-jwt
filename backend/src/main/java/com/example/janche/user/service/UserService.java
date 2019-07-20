package com.example.janche.user.service;

import com.example.janche.user.domain.User;
import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2019-07-20 04:26:39
*/
public interface UserService extends Service<User> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param query  查询关键字
     * @return
     */
    List<User> list(PageParam pageParam, String query);

}
