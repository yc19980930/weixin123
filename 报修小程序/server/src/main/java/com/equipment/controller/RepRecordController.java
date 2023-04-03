package com.equipment.controller;

import com.equipment.common.page.PageResult;
import com.equipment.common.response.R;
import com.equipment.entity.RepRecord;
import com.equipment.service.RepRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 维修记录表 前端控制器
 * </p>
 *
 * @author 小郭
 */
@RestController
@RequestMapping("/record")
public class RepRecordController {


    @Autowired
    private RepRecordService repRecordService;
    
    
    /**
     *  分页条件查询
     * @param page 当前页码
     * @param limit 每页的大小
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody RepRecord repRecord){
        PageResult pageResult = repRecordService.pageQuery(page, limit, repRecord);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 查询所有学生
     * @return
     */
    @GetMapping("getAll")
    public R getAll(){
        List<RepRecord> list = repRecordService.list(null);
        return R.ok().data("data", list);
    }

    /**
     * 修改操作
     * @param sysStudent
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody RepRecord sysStudent){
        repRecordService.updateById(sysStudent);
        return R.ok();
    }

    /**
     * 新增操作
     * @param sysStudent
     * @return
     */
    @PostMapping("install")
    public R install(@RequestBody RepRecord sysStudent){
        sysStudent.setStatus("0");
        repRecordService.save(sysStudent);
        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        repRecordService.removeById(id);
        return R.ok();
    }
}

