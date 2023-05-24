package com.smart.classroom.subscription.infrastructure.rpc.quote.converter;


import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.misc.facade.biz.quote.response.ColumnQuoteDTO;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.quote.vo.ColumnQuoteVO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class ColumnQuoteDTO2VOConverter {

    /**
     * 将DO转换成模型
     */
    public static ColumnQuoteVO convert(ColumnQuoteDTO columnQuoteDTO) {
        if (columnQuoteDTO == null) {
            return null;
        }

        return ColumnQuoteVO.builder()
                .id(columnQuoteDTO.getId())
                .createTime(columnQuoteDTO.getCreateTime())
                .updateTime(columnQuoteDTO.getUpdateTime())
                .columnId(columnQuoteDTO.getColumnId())
                .editorId(columnQuoteDTO.getEditorId())
                .price(columnQuoteDTO.getPrice())
                .status(columnQuoteDTO.getStatus())

                .build();
    }


}
