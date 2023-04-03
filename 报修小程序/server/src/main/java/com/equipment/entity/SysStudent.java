package com.equipment.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生信息表
 * </p>
 *
 * @author 小郭
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 二级学院名称
     */
    private String secondarySchools;

    /**
     * 年级
     */
    private String grade;

    /**
     * 审批状态 0,未审核  1,审核通过
     */
    private String status;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic// 添加逻辑删除注解
    private boolean isDeleted;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTime;


}
