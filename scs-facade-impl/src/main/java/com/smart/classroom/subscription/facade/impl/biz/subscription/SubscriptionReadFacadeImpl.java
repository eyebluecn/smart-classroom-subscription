package com.smart.classroom.subscription.facade.impl.biz.subscription;

import com.smart.classroom.subscription.application.biz.subscription.SubscriptionCommandAppService;
import com.smart.classroom.subscription.domain.biz.order.info.PrepareSubscribeInfo;
import com.smart.classroom.subscription.domain.biz.subscription.enums.SubscriptionStatus;
import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.domain.biz.subscription.repository.SubscriptionRepository;
import com.smart.classroom.subscription.domain.biz.subscription.repository.query.SubscriptionPageQuery;
import com.smart.classroom.subscription.domain.rpc.column.ColumnFacadeClient;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.reader.ReaderFacadeClient;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;
import com.smart.classroom.subscription.facade.biz.order.response.OrderDTO;
import com.smart.classroom.subscription.facade.biz.subscription.SubscriptionReadFacade;
import com.smart.classroom.subscription.facade.biz.subscription.request.PrepareSubscribeRequest;
import com.smart.classroom.subscription.facade.biz.subscription.request.ReaderColumnQueryRequest;
import com.smart.classroom.subscription.facade.biz.subscription.request.SubscriptionPageRequest;
import com.smart.classroom.subscription.facade.biz.subscription.response.PrepareSubscribeDTO;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;
import com.smart.classroom.subscription.facade.common.resposne.PagerResponse;
import com.smart.classroom.subscription.facade.impl.biz.order.converter.OrderModel2DTOConverter;
import com.smart.classroom.subscription.facade.impl.biz.subscription.converter.SubscriptionModel2DTOConverter;
import com.smart.classroom.subscription.utility.enums.SortDirection;
import com.smart.classroom.subscription.utility.exception.UtilException;
import com.smart.classroom.subscription.utility.result.Pager;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@DubboService
public class SubscriptionReadFacadeImpl implements SubscriptionReadFacade {

    @Resource
    SubscriptionCommandAppService subscriptionCommandAppService;

    @Resource
    ReaderFacadeClient readerFacadeClient;

    @Resource
    ColumnFacadeClient columnFacadeClient;

    @Resource
    SubscriptionRepository subscriptionRepository;

    /**
     * 发起订阅请求
     */
    public PrepareSubscribeDTO prepareSubscribe(PrepareSubscribeRequest request) {

        PrepareSubscribeInfo prepareSubscribeInfo = subscriptionCommandAppService.prepareSubscribe(request.getReaderId(), request.getColumnId(), request.getPayMethod());

        OrderDTO orderDTO = OrderModel2DTOConverter.convert(prepareSubscribeInfo.getOrderModel());

        PrepareSubscribeDTO prepareSubscribeDTO = new PrepareSubscribeDTO(
                orderDTO,
                prepareSubscribeInfo.getThirdTransactionNo(),
                prepareSubscribeInfo.getNonceStr()
        );

        return prepareSubscribeDTO;


    }


    @Override
    public SubscriptionDTO queryByColumnIdAndReaderId(ReaderColumnQueryRequest readerColumnQueryRequest) {

        //1. 查询读者信息。
        ReaderVO readerVO = readerFacadeClient.queryById(readerColumnQueryRequest.getReaderId());
        if (readerVO == null) {
            throw new UtilException("id={}对应的读者不存在", readerColumnQueryRequest.getReaderId());
        }


        //2. 查询专栏信息。
        ColumnVO columnVO = columnFacadeClient.queryById(readerColumnQueryRequest.getColumnId());
        if (columnVO == null) {
            throw new UtilException("id={}对应的专栏不存在", readerColumnQueryRequest.getColumnId());
        }

        //3. 查找订阅关系是否已经创建了。
        SubscriptionModel subscriptionModel = subscriptionRepository.queryByReaderAndColumn(readerVO, columnVO);

        return SubscriptionModel2DTOConverter.convert(subscriptionModel);
    }

    @Override
    public PagerResponse<SubscriptionDTO> page(SubscriptionPageRequest subscriptionPageRequest) {

        Pager<SubscriptionModel> subscriptionModelPager = subscriptionRepository.page(new SubscriptionPageQuery(
                subscriptionPageRequest.getPageNum(),
                subscriptionPageRequest.getPageSize(),
                SortDirection.toEnum(subscriptionPageRequest.getOrderCreateTime()),
                SortDirection.toEnum(subscriptionPageRequest.getOrderUpdateTime()),
                subscriptionPageRequest.getReaderId(),
                subscriptionPageRequest.getColumnId(),
                subscriptionPageRequest.getOrderId(),
                SubscriptionStatus.toEnum(subscriptionPageRequest.getStatus())
        ));

        List<SubscriptionDTO> list = subscriptionModelPager.getData().stream().map(SubscriptionModel2DTOConverter::convert).collect(Collectors.toList());


        return new PagerResponse<>(subscriptionModelPager.getPageNum(), subscriptionModelPager.getPageSize(), subscriptionModelPager.getTotalItems(), list);
    }
}
