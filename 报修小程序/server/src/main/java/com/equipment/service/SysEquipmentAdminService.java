package com.equipment.service;

import com.equipment.entity.SysEquipmentAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 维修管理员关联设备表 服务类
 * </p>
 *
 * @author 小郭
 */
public interface SysEquipmentAdminService extends IService<SysEquipmentAdmin> {

    /**
     * 通过管理员id获取绑定的设备id
     * @param id 管理员id
     * @return
     */
    List<String> getByAdminId(String id);
}
