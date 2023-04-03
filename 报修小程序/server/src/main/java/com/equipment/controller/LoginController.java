package com.equipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equipment.common.response.R;
import com.equipment.entity.SysRepairAdmin;
import com.equipment.service.SysRepairAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class LoginController {

    @Autowired
    private SysRepairAdminService repairAdminService;


    /**
     * 登录接口
     * @param sysRepairAdmin
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody SysRepairAdmin sysRepairAdmin){
        if(sysRepairAdmin == null){
            return R.error("请输入账号或密码");
        }
        System.out.println(sysRepairAdmin.getUsername());
        // 获取账号信息
        SysRepairAdmin username = repairAdminService.getOne(new QueryWrapper<SysRepairAdmin>().eq("username", sysRepairAdmin.getUsername()));
        if(username == null){
            return R.error("登录账号不存在");
        }
        if(!username.getPassword().equals(sysRepairAdmin.getPassword())){
            return R.error("密码不正确");
        }
        if("0".equals(username.getStatus())){
            return R.error("账号已被禁用");
        }

        return R.ok().data("token", username.getUsername());
    }
}
