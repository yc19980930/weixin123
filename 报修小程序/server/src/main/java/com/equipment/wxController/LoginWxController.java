package com.equipment.wxController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equipment.common.response.R;
import com.equipment.entity.SysStudent;
import com.equipment.service.SysStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  对微信小程序登录注册功能
 * </p>
 *
 * @author 小郭
 */
@RestController
@RequestMapping("/wx")

public class LoginWxController {

    @Autowired
    private SysStudentService sysStudentService;

    /**
     * 登录功能
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody SysStudent sysStudent){
        if(sysStudent == null){
            return R.error("账号和密码不能为空");
        }
        SysStudent username = sysStudentService.getOne(new QueryWrapper<SysStudent>().eq("username", sysStudent.getUsername()));
        if(username == null){
            return R.error("账号不存在");
        }
        if(!username.getPassword().equals(sysStudent.getPassword())){
            return R.error("密码不正确");
        }
        if("0".equals(username.getStatus())){
            return R.error("账号未审核,请联系管理员");
        }
        return R.ok().data("token", username.getId());
    }

    /**
     * 注册功能
     * @return
     */
    @PostMapping("register")
    public R register(@RequestBody SysStudent sysStudent){
        if(sysStudent == null){
            return R.error("注册数据为空");
        }
        sysStudent.setStatus("0"); // 默认设置未审核
        sysStudentService.save(sysStudent);
        return R.ok();
    }
}
