package com.smart.classroom.subscription.repository.impl.subscription;


import com.github.pagehelper.Page;
import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.domain.biz.subscription.repository.SubscriptionRepository;
import com.smart.classroom.subscription.domain.biz.subscription.repository.query.SubscriptionPageQuery;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;
import com.smart.classroom.subscription.repository.impl.subscription.converter.SubscriptionDO2ModelConverter;
import com.smart.classroom.subscription.repository.impl.subscription.converter.SubscriptionModel2DOConverter;
import com.smart.classroom.subscription.repository.mapper.subscription.SubscriptionMapper;
import com.smart.classroom.subscription.repository.orm.subscription.SubscriptionDO;
import com.smart.classroom.subscription.utility.result.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class SubscriptionRepositoryImpl implements SubscriptionRepository {


    @Autowired
    SubscriptionMapper subscriptionMapper;


    @Override
    public SubscriptionModel insert(SubscriptionModel subscriptionModel) {

        SubscriptionDO subscriptionDO = SubscriptionModel2DOConverter.convert(subscriptionModel);

        subscriptionMapper.insert(subscriptionDO);

        //查询一次。
        subscriptionDO = subscriptionMapper.queryById(subscriptionDO.getId());

        return SubscriptionDO2ModelConverter.convert(subscriptionDO);
    }

    @Override
    public SubscriptionModel queryByReaderAndColumn(ReaderVO readerVO, ColumnVO columnVO) {

        SubscriptionDO subscriptionDO = subscriptionMapper.queryByReaderIdAndColumnId(readerVO.getId(), columnVO.getId());

        return SubscriptionDO2ModelConverter.convert(subscriptionDO);
    }

    @Override
    public Pager<SubscriptionModel> page(SubscriptionPageQuery subscriptionPageQuery) {
        Page<SubscriptionDO> page = subscriptionMapper.page(
                subscriptionPageQuery.getPageNum(),
                subscriptionPageQuery.getPageSize(),
                subscriptionPageQuery.getOrderCreateTime(),
                subscriptionPageQuery.getOrderUpdateTime(),
                subscriptionPageQuery.getReaderId(),
                subscriptionPageQuery.getColumnId(),
                subscriptionPageQuery.getOrderId(),
                subscriptionPageQuery.getStatus()
        );

        List<SubscriptionModel> subscriptionModelList = page.stream().map(SubscriptionDO2ModelConverter::convert).collect(Collectors.toList());

        return new Pager<>(page.getPageNum(), page.getPageSize(), page.getTotal(), subscriptionModelList);
    }
}
