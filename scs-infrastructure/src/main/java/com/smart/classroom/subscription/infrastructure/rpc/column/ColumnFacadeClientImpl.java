package com.smart.classroom.subscription.infrastructure.rpc.column;

import com.smart.classroom.misc.facade.biz.column.ColumnReadFacade;
import com.smart.classroom.misc.facade.biz.column.response.ColumnDTO;
import com.smart.classroom.subscription.domain.rpc.column.ColumnFacadeClient;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.infrastructure.rpc.column.converter.ColumnDTO2VOConverter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Service
public class ColumnFacadeClientImpl implements ColumnFacadeClient {

    @DubboReference
    ColumnReadFacade columnReadFacade;

    @Override
    public ColumnVO queryById(long columnId) {

        ColumnDTO columnDTO = columnReadFacade.queryById(columnId);

        return ColumnDTO2VOConverter.convert(columnDTO);
    }
}
