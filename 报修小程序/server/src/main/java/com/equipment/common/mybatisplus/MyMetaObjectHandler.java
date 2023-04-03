package com.equipment.common.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.equipment.common.utils.AceUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 配置mybatisplus自动封装日期类
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        // 参数是属性名字
        this.setFieldValByName("createTime", AceUtils.getCurrentTime(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

}
