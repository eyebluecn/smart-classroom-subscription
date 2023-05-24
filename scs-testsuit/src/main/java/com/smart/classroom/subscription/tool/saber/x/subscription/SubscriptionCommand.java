package com.smart.classroom.subscription.tool.saber.x.subscription;


import com.smart.classroom.subscription.domain.biz.subscription.enums.SubscriptionStatus;
import com.smart.classroom.subscription.tool.saber.command.SaberCommand;
import com.smart.classroom.subscription.tool.saber.support.SaberHelper;
import com.smart.classroom.subscription.utility.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 直接运行该文件，会生成对应的DO，Mapper文件。
 */
public class SubscriptionCommand {
    public static void main(String[] args) {

        /*
         * 	scs_subscription
         */
        SaberCommand saberCommand = new SaberCommand("scs_subscription");

        //模糊筛选
        saberCommand.setFilterLikeFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以


        }});

        //等号筛选
        saberCommand.setFilterEqualFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

            add("reader_id");
            add("column_id");
            add("order_id");
            add("status");
        }});

        //排序
        saberCommand.setFilterOrderFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

        }});

        //枚举定义
        saberCommand.setEnumFieldMap(new HashMap<String, Pair<Class<? extends Enum<?>>, String>>() {{
            put("status", new Pair<>(SubscriptionStatus.class, SubscriptionStatus.CREATED.name()));
        }});

        SaberHelper.run(saberCommand);
    }
}
