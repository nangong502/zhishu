package com.itheima.stock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.stock.pojo.domain.Stock4EvrDayDomain;
import com.itheima.stock.pojo.domain.Stock4MinuteDomain;
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
    /**
     * 根据时间范围和指定的大盘id统计每分钟的交易量
     * @param markedIds 大盘id集合
     * @param startTime 交易开始时间
     * @param endTime 结束时间
     * @return
     */
    List<Map> getStockTradeVol(@Param("markedIds") List<String> markedIds,
                               @Param("startTime") Date startTime,
                               @Param("endTime") Date endTime);
    /**
     * 统计指定时间点下，各个涨跌区间内股票的个数
     * @param avlDate
     * @return
     */
    List<Map> getStockUpDownSectionByTime(@Param("avlDate") Date avlDate);
    /**
     * 根据时间范围查询指定股票的交易流水
     * @param stockCode 股票code
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return
     */
    List<Stock4MinuteDomain> getStockInfoByCodeAndDate(@Param("stockCode") String stockCode,
                                                       @Param("startTime") Date startTime,
                                                       @Param("endTime") Date endTime);

    /**
     * 查询指定日期范围内指定股票每天的交易数据
     * @param stockCode 股票code
     * @param startTime 起始时间
     * @param endTime 终止时间
     * @return
     */
    List<Stock4EvrDayDomain> getStockInfo4EvrDay(@Param("stockCode") String stockCode,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    List<Date> getMaxTimeList(@Param("stockCode") String stockCode,
                              @Param("startTime") Date startTime,
                              @Param("endTime") Date endTime);

    List<Stock4EvrDayDomain> getStockInfo4EvrDay2(@Param("stockCode") String stockCode,
                                                  @Param("startTime") Date startTime,
                                                  @Param("endTime") Date endTime,
                                                  @Param("maxTimeList") List<Date> maxTimeList);
}
