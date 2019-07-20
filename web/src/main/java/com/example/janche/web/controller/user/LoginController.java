package com.example.janche.web.controller.user;

import com.example.janche.common.exception.CustomException;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.common.util.CookieUtils;
import com.example.janche.web.config.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lirong
 * @ClassName: LoginController
 * @Description: 登录Controller
 * @date 2019-07-12 9:31
 */
@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录
     */
    @PostMapping("/login")
    public RestResult login(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(required = false, defaultValue = "false") Boolean rememberMe,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication, rememberMe, false);
        String jwt_refresh = jwtUtil.createJWT(authentication, rememberMe, true);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        map.put("refreshToken", jwt_refresh);

        CookieUtils.setCookie(response, "localhost", jwt);
        return ResultGenerator.genSuccessResult().setMessage("登录成功").setData(map);
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public RestResult logout(HttpServletRequest request) {
        try {
            // 设置JWT过期
            jwtUtil.invalidateJWT(request);
        } catch (CustomException e) {
            throw new CustomException(ResultCode.UNAUTHORIZED);
        }
        return ResultGenerator.genSuccessResult().setMessage("退出成功");
    }

    /**
     * 刷新过期的token
     * @param refreshToken
     * @return
     */
    @PostMapping("/refresh/token")
    public RestResult refreshToken(String refreshToken) {
        Map<String, String> map;
        try {
            // 刷新
            map = jwtUtil.refreshJWT(refreshToken);
        } catch (CustomException e) {
            throw new CustomException(ResultCode.TOKEN_EXPIRED);
        }
        return ResultGenerator.genSuccessResult().setMessage("token刷新成功").setData(map);
    }
}
