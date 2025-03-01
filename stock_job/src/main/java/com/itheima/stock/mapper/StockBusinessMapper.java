package com.itheima.stock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.stock.pojo.entity.StockBusiness;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 主营业务表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Mapper
public interface StockBusinessMapper extends BaseMapper<StockBusiness> {
    /**
     * 获取所有股票的code
     * @return
     */
    List<String> getStockIds();
}
