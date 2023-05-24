package com.smart.classroom.subscription.tool.saber.x.order;


import com.smart.classroom.subscription.domain.biz.order.enums.OrderStatus;
import com.smart.classroom.subscription.tool.saber.command.SaberCommand;
import com.smart.classroom.subscription.tool.saber.support.SaberHelper;
import com.smart.classroom.subscription.utility.model.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 直接运行该文件，会生成对应的DO，Mapper文件。
 */
public class OrderCommand {
    public static void main(String[] args) {

        /*
         * 	scs_order
         */
        SaberCommand saberCommand = new SaberCommand("scs_order");

        //模糊筛选
        saberCommand.setFilterLikeFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以


        }});

        //等号筛选
        saberCommand.setFilterEqualFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

            add("reader_id");
            add("column_id");
            add("column_quote_id");
            add("payment_id");
            add("status");
        }});

        //排序
        saberCommand.setFilterOrderFields(new ArrayList<String>() {{
            //下划线风格，驼峰均可以

        }});

        //枚举定义
        saberCommand.setEnumFieldMap(new HashMap<String, Pair<Class<? extends Enum<?>>, String>>() {{
            put("status", new Pair<>(OrderStatus.class, OrderStatus.CREATED.name()));
        }});

        SaberHelper.run(saberCommand);
    }
}
