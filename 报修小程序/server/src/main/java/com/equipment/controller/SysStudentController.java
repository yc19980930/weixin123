package com.equipment.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.equipment.common.response.R;
import com.equipment.entity.SysStudent;
import com.equipment.service.SysStudentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学生信息表 前端控制器
 * </p>
 *
 * @author 小郭
 */
@RestController
@RequestMapping("/student")
public class SysStudentController {

    @Autowired
    private SysStudentService sysStudentService;

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
            @RequestBody SysStudent sysStudent){

        QueryWrapper<SysStudent> qw = new QueryWrapper<>();
        LambdaQueryWrapper<SysStudent> lambda = qw.lambda();

        // 按学生姓名查询
        if(Strings.isNotEmpty(sysStudent.getName())){
            lambda.eq(SysStudent::getName, sysStudent.getName());
        }

        // 按审核状态查询查询
        if(Strings.isNotEmpty(sysStudent.getStatus())){
            lambda.eq(SysStudent::getStatus, sysStudent.getStatus());
        }

        // 按时间排序
        lambda.orderByDesc(SysStudent::getCreateTime);


        Page<SysStudent> pageParam = new Page<>(page, limit);

        IPage<SysStudent> data = sysStudentService.page(pageParam, qw);

        return R.ok().data("rows",data.getRecords()).data("total",data.getTotal());
    }

    /**
     * 查询所有学生
     * @return
     */
    @GetMapping("getAll")
    public R getAll(){
        List<SysStudent> list = sysStudentService.list(null);
        return R.ok().data("data", list);
    }

    /**
     * 修改操作
     * @param sysStudent
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody SysStudent sysStudent){
        sysStudentService.updateById(sysStudent);
        return R.ok();
    }

    /**
     * 新增操作
     * @param sysStudent
     * @return
     */
    @PostMapping("install")
    public R install(@RequestBody SysStudent sysStudent){
        sysStudent.setStatus("0");
        sysStudentService.save(sysStudent);
        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        sysStudentService.removeById(id);
        return R.ok();
    }
}

