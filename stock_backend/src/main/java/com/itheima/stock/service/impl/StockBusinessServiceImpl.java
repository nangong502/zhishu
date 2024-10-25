package com.itheima.stock.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.stock.mapper.StockBusinessMapper;
import com.itheima.stock.service.IStockBusinessService;
import com.itheima.stock.pojo.entity.StockBusiness;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 主营业务表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Service
public class StockBusinessServiceImpl extends ServiceImpl<StockBusinessMapper, StockBusiness> implements IStockBusinessService {

}
