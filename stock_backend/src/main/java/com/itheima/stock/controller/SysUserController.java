package com.itheima.stock.controller;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.itheima.stock.pojo.entity.SysUser;
import com.itheima.stock.service.ISysUserService;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.R;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SysUserController {
    private final ISysUserService iSysUserService;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param userName
     * @return com.itheima.stock.pojo.entity.SysUser
     * @author nangong
     * @dateTime 2024/10/25 13:27
     * @desp 根据用户名称返回用户信息
     */
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String userName) {
        SysUser user = iSysUserService.lambdaQuery()
                .eq(SysUser::getUsername, userName).one();
        return user;
    }

    /**
     * @author nangong
     * @dateTime 2024/10/25 14:23
     * @param reqVo
     * @return com.itheima.stock.vo.resp.R<com.itheima.stock.vo.resp.LoginRespVo>
     * @desp 用户登录接口
     */
    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo reqVo) {
        SysUser one = iSysUserService.lambdaQuery()
                .eq(SysUser::getUsername, reqVo.getUsername()).one();
        if (one == null) return R.error("无该用户");
        boolean matches = passwordEncoder.matches(reqVo.getPassword(),one.getPassword());
        if (!matches) return R.error("密码错误");
        //封装
        LoginRespVo respVo = new LoginRespVo();
        BeanUtils.copyProperties(one, respVo);
        return R.ok(respVo);
    }
}
