package com.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.equipment.common.page.PageResult;
import com.equipment.entity.MsgEquipment;
import com.equipment.entity.RepRecord;
import com.equipment.mapper.RepRecordMapper;
import com.equipment.service.MsgEquipmentService;
import com.equipment.service.RepRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 维修记录表 服务实现类
 * </p>
 *
 * @author 小郭
 */
@Service
public class RepRecordServiceImpl extends ServiceImpl<RepRecordMapper, RepRecord> implements RepRecordService {

    @Autowired
    private MsgEquipmentService msgEquipmentService;


    @Override
    public int insertRepRecord(RepRecord repRecord) {
        // 通过设备名称获取设备id
        String equipmentId = repRecord.getEquipmentId();
        MsgEquipment msgEquipment = msgEquipmentService.getOne(new QueryWrapper<MsgEquipment>().eq("name", equipmentId));
        repRecord.setEquipmentId(msgEquipment.getId());
        repRecord.setStatus("0"); // 默认设置处理状态未处理
        // 执行新增操作
        return baseMapper.insert(repRecord);
    }

    @Override
    public PageResult pageQuery(int page, int limit, RepRecord repRecord) {
        PageHelper.startPage(page,limit);
        List<RepRecord> queryList = baseMapper.getQueryList(repRecord);
        PageInfo<RepRecord> pageInfo = new PageInfo<>(queryList);

        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public List<RepRecord> getRepRecord(RepRecord repRecord) {
        return baseMapper.getQueryList(repRecord);
    }
}
