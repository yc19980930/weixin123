package com.equipment.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equipment.common.response.R;
import com.equipment.entity.SysEquipmentAdmin;
import com.equipment.service.SysEquipmentAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 维修管理员关联设备表 前端控制器
 * </p>
 *
 * @author 小郭
 */
@RestController
@RequestMapping("/equipmentAdmin")
public class SysEquipmentAdminController {

    @Autowired
    private SysEquipmentAdminService sysEquipmentAdminService;


    /**
     * 通过管理员id获取绑定的设备id
     * @param id 管理员id
     * @return
     */
    @GetMapping("getByAdminId/{id}")
    public R getByAdminId(@PathVariable String id){
        List<String> list = sysEquipmentAdminService.getByAdminId(id);
        return R.ok().data("data", list);
    }

    /**
     * 更新管理员id关联设备信息
     * @param id
     * @param equipmentList
     * @return
     */
   @PutMapping("updateAdminEquipment/{id}")
    public R updateAdminEquipment(@PathVariable String id, @RequestBody List<String> equipmentList){


       // 删除绑定设备信息
       sysEquipmentAdminService.remove(new QueryWrapper<SysEquipmentAdmin>().eq("admin_id",id));

       // 新增设备绑定信息
       for (String equipmentId : equipmentList) {
           SysEquipmentAdmin sysEquipmentAdmin = new SysEquipmentAdmin();
           sysEquipmentAdmin.setAdminId(id);
           sysEquipmentAdmin.setEquipmentId(equipmentId);
           sysEquipmentAdminService.save(sysEquipmentAdmin);
       }

        return R.ok();
   }
}

