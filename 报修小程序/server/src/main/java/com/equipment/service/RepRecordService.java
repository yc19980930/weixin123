package com.equipment.service;

import com.equipment.common.page.PageResult;
import com.equipment.entity.RepRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 维修记录表 服务类
 * </p>
 *
 * @author 小郭
 */
public interface RepRecordService extends IService<RepRecord> {

    /**
     * 新增维修记录
     * @param repRecord
     */
    int insertRepRecord(RepRecord repRecord);

    /**
     * 分页条件查询
     * @param page 当前页面
     * @param limit 每页的大小
     * @param repRecord 查询条件
     * @return
     */
    PageResult pageQuery(int page, int limit, RepRecord repRecord);


    /**
     * 条件查询
     * @param repRecord 查询条件
     * @return
     */
    List<RepRecord> getRepRecord(RepRecord repRecord);
}
