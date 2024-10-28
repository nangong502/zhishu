package com.itheima.stock.controller;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.itheima.stock.pojo.constant.StockConstant;
import com.itheima.stock.pojo.entity.SysUser;
import com.itheima.stock.pojo.utils.IdWorker;
import com.itheima.stock.service.ISysUserService;
import com.itheima.stock.vo.req.LoginReqVo;
import com.itheima.stock.vo.resp.LoginRespVo;
import com.itheima.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
@Slf4j
@Api(tags = "用户相关接口")
public class SysUserController {
    private final ISysUserService iSysUserService;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;
    private final IdWorker idWorker;
    /**
     * @param userName
     * @return com.itheima.stock.pojo.entity.SysUser
     * @author nangong
     * @dateTime 2024/10/25 13:27
     * @desp 根据用户名称返回用户信息
     */
    @ApiOperation("根据用户名称返回用户信息")
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
        //验证码校验
        String s = stringRedisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + reqVo.getSessionId());
        if(s==null||!s.equals(reqVo.getCode())) return R.error("验证码校验失败");
        //封装
        LoginRespVo respVo = new LoginRespVo();
        BeanUtils.copyProperties(one, respVo);
        return R.ok(respVo);
    }

    /**
     * @author nangong
     * @dateTime 2024/10/25 19:40
     * @param
     * @return com.itheima.stock.vo.resp.R<java.util.Map<java.lang.String,java.lang.String>>
     * @desp 生成登录验证码
     */
    @GetMapping("captcha")
    public R<Map<String,String>> getcaptcha(){
        //参数分别是宽、高、验证码长度、干扰线数量
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        //设置背景颜色清灰
        captcha.setBackground(Color.lightGray);
        //获取图片中的验证码，默认生成的校验码包含文字和数字，长度为4
        String checkCode = captcha.getCode();
        log.info("生成校验码:{}",checkCode);
        //生成sessionId
        String sessionId = String.valueOf(idWorker.nextId());
        //将sessionId和校验码保存在redis下，并设置缓存中数据存活时间一分钟
        stringRedisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX +sessionId,checkCode,3, TimeUnit.MINUTES);
        //组装响应数据
        HashMap<String, String> info = new HashMap<>();
        info.put("sessionId",sessionId);
        info.put("imageData",captcha.getImageBase64());//获取base64格式的图片数据
        //设置响应数据格式
        return R.ok(info);
    }

}
