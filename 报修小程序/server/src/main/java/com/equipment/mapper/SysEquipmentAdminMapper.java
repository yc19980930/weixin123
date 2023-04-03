package com.equipment.mapper;

import com.equipment.entity.SysEquipmentAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 维修管理员关联设备表 Mapper 接口
 * </p>
 *
 * @author 小郭
 */
public interface SysEquipmentAdminMapper extends BaseMapper<SysEquipmentAdmin> {


    List<String> getByAdminId(String id);
}
