package com.equipment.service;

import com.equipment.entity.MsgEquipment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备信息表 服务类
 * </p>
 *
 * @author 小郭
 */
public interface MsgEquipmentService extends IService<MsgEquipment> {

    /**
     * 通过维修管理员id负责的设备
     * @param adminId
     * @return
     */
    List<String> getAdminEquipment(String adminId);

}
