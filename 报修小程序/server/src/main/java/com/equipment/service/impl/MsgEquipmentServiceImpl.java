package com.equipment.service.impl;

import com.equipment.entity.MsgEquipment;
import com.equipment.mapper.MsgEquipmentMapper;
import com.equipment.service.MsgEquipmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备信息表 服务实现类
 * </p>
 *
 * @author 小郭
 */
@Service
public class MsgEquipmentServiceImpl extends ServiceImpl<MsgEquipmentMapper, MsgEquipment> implements MsgEquipmentService {

    @Override
    public List<String> getAdminEquipment(String adminId) {
        return baseMapper.getAdminEquipment(adminId);
    }
}
