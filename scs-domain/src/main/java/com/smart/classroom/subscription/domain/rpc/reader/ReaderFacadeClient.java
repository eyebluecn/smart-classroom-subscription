package com.smart.classroom.subscription.domain.rpc.reader;

import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;

/**
 * @author lishuang
 * @date 2023-05-15
 */
public interface ReaderFacadeClient {

    ReaderVO queryById(long readerId);

}
