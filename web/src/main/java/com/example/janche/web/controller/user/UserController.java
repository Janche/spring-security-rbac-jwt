package com.example.janche.web.controller.user;

import com.example.janche.user.domain.User;
import com.example.janche.user.service.UserService;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.web.aop.Log;
import com.example.janche.common.restResult.PageParam;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
*
* @author lirong
* @Description: 用户管理模块
* @date 2019-07-20 04:26:39
*/
@Slf4j
@RestController
@RequestMapping("/user")
@Api(basePath = "/user", tags = "用户模块管理")
public class UserController {
    @Resource
    private UserService userService;

    @Log
    @PostMapping
    @ApiOperation(value = "新增用户", notes = "单个新增", produces = "application/json")
    public RestResult add(@ApiParam(name = "用户信息", required = true) User user) {
        user.setCreateTime(new Date());
        userService.save(user);
        return ResultGenerator.genSuccessResult().setMessage("保存成功");
    }

    @Log
    @DeleteMapping
    @ApiOperation(value = "删除用户", notes = "单个删除", produces = "application/json")
    public RestResult delete(@ApiParam(name = "用户信息", required = true) Long id) {
        userService.deleteById(id);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log
    @PutMapping
    @ApiOperation(value = "修改用户", notes = "单个修改" , code = 200, produces = "application/json")
    public RestResult update(@ApiParam(name = "用户信息", required = true) User user) {
        user.setModifyTime(new Date());
        userService.update(user);
        return ResultGenerator.genSuccessResult().setMessage("修改成功");
    }

    @Log
    @GetMapping
    @ApiOperation(value = "获取用户信息", notes = "单个获取", code = 200, produces = "application/json")
    public RestResult detail(@ApiParam(value = "主键ID") @RequestParam Long id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    /**
     * 用于分页查询,默认可以不用传分页信息
     * 默认值：page=1,size=10,sortField="id",sortOrder="ASC"
     */
    @Log
    @GetMapping(value = "/list")
    @ApiOperation(value = "用户列表分页查询", notes = "分页列表", code = 200, produces = "application/json")
    public RestResult list(@ApiParam(value = "分页信息") PageParam pageParam,
                           @ApiParam(value = "查询条件") @RequestParam(required = false, defaultValue = "") String query) {
        List<User> list = userService.list(pageParam, query);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 下拉框查询所有
     */
    @Log
    @ApiOperation(value = "用户列表查询所有", notes = "下拉框列表", code = 200, produces = "application/json")
    @GetMapping(value = "/all")
    public RestResult listAll() {
        List<User> list = userService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

}
