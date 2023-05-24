package com.smart.classroom.subscription.infrastructure.rpc.column.converter;


import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ColumnDTO2VOConverter {

    /**
     * 将DO转换成模型
     */
    public static ColumnVO convert(ColumnDTO columnDTO) {
        if (columnDTO == null) {
            return null;
        }

        return ColumnVO.builder()
                .id(columnDTO.getId())
                .createTime(columnDTO.getCreateTime())
                .updateTime(columnDTO.getUpdateTime())
                .name(columnDTO.getName())
                .authorId(columnDTO.getAuthorId())
                .status(columnDTO.getStatus())

                .build();
    }


}
