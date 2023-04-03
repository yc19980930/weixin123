package com.equipment.wxController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.equipment.common.response.R;
import com.equipment.entity.MsgEquipment;
import com.equipment.entity.RepRecord;
import com.equipment.entity.SysRepairAdmin;
import com.equipment.service.MsgEquipmentService;
import com.equipment.service.RepRecordService;
import com.equipment.service.SysRepairAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  对微信小程序提供给维修记录相关接口
 * </p>
 *
 * @author 小郭
 */
@RestController
@RequestMapping("/wxRecord")
public class RepRecordWxController {

    @Autowired
    private MsgEquipmentService msgEquipmentService;

    @Autowired
    private RepRecordService repRecordService;

    @Autowired
    private SysRepairAdminService sysRepairAdminService;

    /**
     * 查询所有设备信息
     */
    @GetMapping("getEquipmentAll")
    public R getEquipmentAll(){
        List<MsgEquipment> list = msgEquipmentService.list(new QueryWrapper<MsgEquipment>().orderByDesc("create_time"));
        return R.ok().data("row",list);
    }


    /**
     * 通过条件获取设备维修记录(学生id, 设备名称, 状态 0,未处理  1,已处理)
     * @return
     */
    @PostMapping("getByStudentIdRecord")
    public R getByStudentIdRecord(@RequestBody RepRecord repRecord){


        // 按照设备id查询
        if(StringUtils.isNotEmpty(repRecord.getEquipmentName())){

            MsgEquipment name = msgEquipmentService.getOne(new QueryWrapper<MsgEquipment>().eq("name", repRecord.getEquipmentName()));
            repRecord.setEquipmentId(name.getId());
        }
        List<RepRecord> list = repRecordService.getRepRecord(repRecord);
        return R.ok().data("row", list);
    }

    /**
     * 维修记录id获取记录
     */
    @GetMapping("getRepairById/{id}")
    public R getRepairById(@PathVariable String id){
        RepRecord byId = repRecordService.getById(id);
        MsgEquipment id1 = msgEquipmentService.getOne(new QueryWrapper<MsgEquipment>().eq("id", byId.getEquipmentId()));
        byId.setEquipmentName(id1.getName());
        return R.ok().data("row", byId);
    }


    /**
     * 申请维修记录
     * @param repRecord
     * @return
     */
    @PostMapping("insertRepRecord")
    public R insertRepRecord(@RequestBody RepRecord repRecord){
        if(repRecord == null){
            return R.error("申请数据为空!");
        }
        int i = repRecordService.insertRepRecord(repRecord);
        if(i > 0){
            return R.ok();
        }else{
            return R.error();
        }

    }

    /**
     * 查询所有维修管理员信息
     */
    @GetMapping("getAdmin")
    public R getAdmin(){
        List<SysRepairAdmin> list = sysRepairAdminService.list(new QueryWrapper<SysRepairAdmin>().eq("status", "1").orderByDesc("create_time"));
        for (SysRepairAdmin sysRepairAdmin : list) {
            // 通过管理员id获取关联设备名称
            List<String> adminEquipment = msgEquipmentService.getAdminEquipment(sysRepairAdmin.getId());
            sysRepairAdmin.setEquipmentNameList(adminEquipment);
        }
        return R.ok().data("data", list);
    }
}
