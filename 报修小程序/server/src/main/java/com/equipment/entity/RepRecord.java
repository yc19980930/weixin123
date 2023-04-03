package com.equipment.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 维修记录表
 * </p>
 *
 * @author 小郭
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RepRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 设备id(关联msg_equipment)
     */
    private String equipmentId;

    /**
     * 维修地址
     */
    private String address;

    /**
     * 维修描述
     */
    private String recordDesc;

    /**
     * 紧急维修状态  0: 普通维修  1: 紧急维修
     */
    private String level;

    /**
     * 维修状态  0: 未处理  1: 已处理
     */
    private String status;

    /**
     * 申请时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    /**
     * 申请联系人电话
     */
    private String phone;

    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 申请学生信息id(管理sys_student表)
     */
    private String studentId;


    /**
     * 用于前端展示学生账号
     */
    @TableField(exist = false)
    private String studentUsername;

    /**
     * 用于前端展示设备名称
     */
    @TableField(exist = false)
    private String equipmentName;
}
