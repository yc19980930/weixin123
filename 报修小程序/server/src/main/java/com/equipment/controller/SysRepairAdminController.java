package com.equipment.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.equipment.common.response.R;
import com.equipment.entity.SysRepairAdmin;
import com.equipment.service.SysRepairAdminService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 维修管理员信息表 前端控制器
 * </p>
 *
 * @author 小郭
 */
@RestController
@RequestMapping("/admin")
public class SysRepairAdminController {


    @Autowired
    private SysRepairAdminService sysRepairAdminService;

    /**
     *  分页条件查询
     * @param page 当前页码
     * @param limit 每页的大小
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
            @PathVariable long page,
            @PathVariable long limit,
            @RequestBody SysRepairAdmin sysRepairAdmin){

        QueryWrapper<SysRepairAdmin> qw = new QueryWrapper<>();
        LambdaQueryWrapper<SysRepairAdmin> lambda = qw.lambda();

        // 按管理员姓名查询
        if(Strings.isNotEmpty(sysRepairAdmin.getName())){
            lambda.eq(SysRepairAdmin::getName, sysRepairAdmin.getName());
        }

        // 按手机号查询
        if(Strings.isNotEmpty(sysRepairAdmin.getPhone())){
            lambda.eq(SysRepairAdmin::getPhone, sysRepairAdmin.getPhone());
        }

        // 按账号状态查询
        if(Strings.isNotEmpty(sysRepairAdmin.getStatus())){
            lambda.eq(SysRepairAdmin::getStatus, sysRepairAdmin.getStatus());
        }

        // 按时间排序
        lambda.orderByDesc(SysRepairAdmin::getCreateTime);

        Page<SysRepairAdmin> pageParam = new Page<>(page, limit);

        IPage<SysRepairAdmin> data = sysRepairAdminService.page(pageParam, qw);

        return R.ok().data("rows",data.getRecords()).data("total",data.getTotal());
    }

    /**
     * 查询所有管理员
     * @return
     */
    @GetMapping("getAll")
    public R getAll(){
        List<SysRepairAdmin> list = sysRepairAdminService.list(null);
        return R.ok().data("data", list);
    }

    /**
     * 修改操作
     * @param sysStudent
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody SysRepairAdmin sysStudent){
        sysRepairAdminService.updateById(sysStudent);
        return R.ok();
    }

    /**
     * 新增操作
     * @param sysStudent
     * @return
     */
    @PostMapping("install")
    public R install(@RequestBody SysRepairAdmin sysStudent){
        sysStudent.setStatus("1"); // 默认设置正常
        sysRepairAdminService.save(sysStudent);
        return R.ok();
    }

    /**
     *删除操作
     * @param id 通过id删除
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        sysRepairAdminService.removeById(id);
        return R.ok();
    }
}

