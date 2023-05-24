package com.smart.classroom.subscription.repository.orm.base;


import lombok.Data;

import java.util.Date;

/**
 * @author fusu
 * @date 2023-04-14
 */
@Data
public class BaseEntityDO {

    public final static String EMPTY_JSON_ARRAY = "[]";
    public final static String EMPTY_JSON_OBJECT = "{}";

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime = new Date();

    /**
     * 修改时间
     */
    private Date updateTime = new Date();

}
