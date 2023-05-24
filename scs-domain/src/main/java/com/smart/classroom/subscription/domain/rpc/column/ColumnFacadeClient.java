package com.smart.classroom.subscription.domain.rpc.column;

import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;

/**
 * @author lishuang
 * @date 2023-05-15
 */
public interface ColumnFacadeClient {

    ColumnVO queryById(long columnId);

}
