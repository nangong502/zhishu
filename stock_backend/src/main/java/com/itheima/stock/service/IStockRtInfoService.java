package com.itheima.stock.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.stock.pojo.domain.Stock4EvrDayDomain;
import com.itheima.stock.pojo.domain.Stock4MinuteDomain;
import com.itheima.stock.pojo.domain.StockUpdownDomain;
import com.itheima.stock.pojo.entity.StockRtInfo;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 个股详情信息表 服务类
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
public interface IStockRtInfoService extends IService<StockRtInfo> {

    /**
     * @author nangong
     * @dateTime 2024/10/28 20:24
     * @param page
     * @param pageSize
     * @return com.itheima.stock.vo.resp.R<com.itheima.stock.vo.resp.PageResult<com.itheima.stock.pojo.domain.StockUpdownDomain>>
     * @desp 涨幅全榜
     */
    R<PageResult<StockUpdownDomain>> getStockPageInfo(int page, int pageSize);

    /**
     * @author nangong
     * @dateTime 2024/10/28 20:24
     * @param
     * @return com.itheima.stock.vo.resp.R<java.util.List<com.itheima.stock.pojo.domain.StockUpdownDomain>>
     * @desp 涨幅小榜
     */
    R<List<StockUpdownDomain>> getStockIncreasePageInfo();

    /**
     * @author nangong
     * @dateTime 2024/10/28 20:41
     * @param
     * @return com.itheima.stock.vo.resp.R<java.util.Map>
     * @desp 统计最新交易日下股票每分钟涨跌停的数量
     */
    R<Map> getStockUpdownCount();
    /**
     * @author nangong
     * @dateTime 2024/10/29 16:02
     * @param response
     * @param page
     * @param pageSize
     * @return void
     * @desp 涨幅榜数据导出
     */
    void stockExport(HttpServletResponse response, Integer page, Integer pageSize);
    /**
     * 功能描述：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * @return
     */
    R<Map> stockTradeVol4InnerMarket();

    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     * @return
     */
    R<Map> stockUpDownScopeCount();
    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param code 股票编码
     * @return
     */
    R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String code);
    /**
     * 单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据
     * @param code 股票编码
     */
    R<List<Stock4EvrDayDomain>> stockCreenDkLine(String code);
}
