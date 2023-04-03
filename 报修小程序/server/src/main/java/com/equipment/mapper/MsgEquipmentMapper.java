package com.equipment.mapper;

import com.equipment.entity.MsgEquipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 设备信息表 Mapper 接口
 * </p>
 *
 * @author 小郭
 */
public interface MsgEquipmentMapper extends BaseMapper<MsgEquipment> {


    List<String> getAdminEquipment(String adminId);
}
