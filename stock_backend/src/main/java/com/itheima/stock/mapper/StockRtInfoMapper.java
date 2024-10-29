package com.itheima.stock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.stock.pojo.domain.StockUpdownDomain;
import com.itheima.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 个股详情信息表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Mapper
public interface StockRtInfoMapper extends BaseMapper<StockRtInfo> {
    /**
     * @author nangong
     * @dateTime 2024/10/28 20:22
     * @param curDate
     * @param page
     * @param pageSize
     * @return java.util.List<com.itheima.stock.pojo.domain.StockUpdownDomain>
     * @desp  涨幅全榜
     */
    List<StockUpdownDomain> stockRtInfoMapper(@Param("timePoint") Date curDate, @Param("page")int page,@Param("pageSize") int pageSize);

    /**
     * @author nangong
     * @dateTime 2024/10/28 20:26
     * @param curDate
     * @return java.util.List<com.itheima.stock.pojo.domain.StockUpdownDomain>
     * @desp 涨幅小榜
     */
    List<StockUpdownDomain> getStockIncreasePageInfo(@Param("timePoint")Date curDate);

    /**
     * @author nangong
     * @dateTime 2024/10/28 20:44
     * @param openTime
     * @param curTime
     * @param i
     * @return java.util.List<java.util.Map>
     * @desp 统计最新交易日下股票每分钟涨跌停的数量
     */
    List<Map> getStockUpdownCount(@Param("openTime")Date openTime, @Param("curTime")Date curTime, @Param("flag")int i);
}
