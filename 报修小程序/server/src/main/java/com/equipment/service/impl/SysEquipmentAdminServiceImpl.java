package com.equipment.service.impl;

import com.equipment.entity.SysEquipmentAdmin;
import com.equipment.mapper.SysEquipmentAdminMapper;
import com.equipment.service.SysEquipmentAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 维修管理员关联设备表 服务实现类
 * </p>
 *
 * @author 小郭
 */
@Service
public class SysEquipmentAdminServiceImpl extends ServiceImpl<SysEquipmentAdminMapper, SysEquipmentAdmin> implements SysEquipmentAdminService {

    @Override
    public List<String> getByAdminId(String id) {
        return baseMapper.getByAdminId(id);
    }
}
