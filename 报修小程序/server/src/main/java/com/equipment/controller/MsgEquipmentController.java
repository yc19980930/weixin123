package com.equipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.equipment.common.response.R;
import com.equipment.entity.MsgEquipment;
import com.equipment.service.MsgEquipmentService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 设备信息表 前端控制器
 *      1. 分页条件查询功能
 *      2. 修改功能
 *      3. 新增功能
 *      4. 通过id删除功能
 * </p>
 *
 * @author 小郭
 */
@RestController
@RequestMapping("/equipment")
public class MsgEquipmentController {


    @Autowired
    private MsgEquipmentService msgEquipmentService;

    /**
     *  分页条件查询
     * @param page 当前页码
     * @param limit 每页的大小
     * @param msgEquipment
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
                         @PathVariable long page,
                         @PathVariable long limit,
                         @RequestBody MsgEquipment msgEquipment){

        QueryWrapper<MsgEquipment> qw = new QueryWrapper<>();
        LambdaQueryWrapper<MsgEquipment> lambda = qw.lambda();

        // 按设备名称模糊查询
        if(Strings.isNotEmpty(msgEquipment.getName())){
            lambda.like(MsgEquipment::getName, msgEquipment.getName());
        }
        // 按时间排序
        lambda.orderByDesc(MsgEquipment::getCreateTime);


        Page<MsgEquipment> pageParam = new Page<>(page, limit);

        IPage<MsgEquipment> data = msgEquipmentService.page(pageParam, qw);

        return R.ok().data("rows",data.getRecords()).data("total",data.getTotal());
    }

    /**
     * 查询所有设备
     * @return
     */
    @GetMapping("getAll")
    public R getAll(){
        List<MsgEquipment> list = msgEquipmentService.list(null);
        return R.ok().data("data", list);
    }

    /**
     * 修改操作
     * @param msgEquipment
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody MsgEquipment msgEquipment){
        msgEquipmentService.updateById(msgEquipment);
        return R.ok();
    }

    /**
     * 新增操作
     * @param msgEquipment
     * @return
     */
    @PostMapping("install")
    public R install(@RequestBody MsgEquipment msgEquipment){
        msgEquipmentService.save(msgEquipment);
        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable String id){
        msgEquipmentService.removeById(id);
        return R.ok();
    }

}

