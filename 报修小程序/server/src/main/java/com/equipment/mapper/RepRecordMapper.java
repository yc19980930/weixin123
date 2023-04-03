package com.equipment.mapper;

import com.equipment.entity.RepRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 维修记录表 Mapper 接口
 * </p>
 *
 * @author 小郭
 */
public interface RepRecordMapper extends BaseMapper<RepRecord> {

    List<RepRecord> getQueryList(RepRecord repRecord);
}
