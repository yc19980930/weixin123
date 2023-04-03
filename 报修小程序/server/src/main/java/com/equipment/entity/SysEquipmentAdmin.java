package com.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 维修管理员关联设备表
 * </p>
 *
 * @author 小郭
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysEquipmentAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 维修管理员id
     */
    private String adminId;

    /**
     * 设备id
     */
    private String equipmentId;


}
