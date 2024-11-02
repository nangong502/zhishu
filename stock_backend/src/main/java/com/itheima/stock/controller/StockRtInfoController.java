package com.itheima.stock.controller;


import cn.hutool.db.meta.Column;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.stock.pojo.domain.*;
import com.itheima.stock.pojo.entity.StockBlockRtInfo;
import com.itheima.stock.pojo.entity.StockRtInfo;
import com.itheima.stock.pojo.utils.DateTimeUtil;
import com.itheima.stock.pojo.vo.StockInfoConfig;
import com.itheima.stock.service.IStockRtInfoService;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import com.baomidou.mybatisplus.core.conditions.query.Column;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 个股详情信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Api(value = "/api/quot", tags = {"<p> 个股详情信息表 前端控制器 </p>"})
@RestController
@RequestMapping("/api/quot")
@RequiredArgsConstructor
public class StockRtInfoController {
    private final IStockRtInfoService stockRtInfoService;

    /**
     * @author nangong
     * @dateTime 2024/10/28 20:18
     * @param page
     * @param pageSize
     * @return com.itheima.stock.vo.resp.R<com.itheima.stock.vo.resp.PageResult<com.itheima.stock.pojo.domain.StockUpdownDomain>>
     * @desp 涨幅全榜
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = ""),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "")
    })
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/stock/all")
    public R<PageResult<StockUpdownDomain>> getStockPageInfo(@RequestParam(required = false, defaultValue = "1")int page, @RequestParam(required = false, defaultValue = "20")int pageSize){
//    Page<StockRtInfo> dividePage = new Page<>(page,pageSize,true);
//    List<OrderItem> list = new ArrayList<>();
//    OrderItem item = new OrderItem();
//    list.add(item);
//    dividePage.addOrder(list);
//    Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
//    curDate= DateTime.parse("2022-06-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
//    LambdaQueryWrapper<StockRtInfo> wrapper = new LambdaQueryWrapper<>();
//    wrapper.eq(StockRtInfo::getCurTime,curDate);
//    stockRtInfoService.page(dividePage,wrapper);
//    PageResult<StockUpdownDomain> pageResult = new PageResult(dividePage);
//    //构建集合
//        List<StockUpdownDomain> rows = new ArrayList<>();
//        for (StockRtInfo record : dividePage.getRecords()) {
//            StockUpdownDomain stockUpdownDomain = new StockUpdownDomain();
//            stockUpdownDomain.setCode(record.getStockCode());
//            stockUpdownDomain.setName(record.getStockName());
//            stockUpdownDomain.setPreClosePrice(record.getPreClosePrice());
//            stockUpdownDomain.setTradePrice(record.getCurPrice());
//            BigDecimal multiply = record.getCurPrice().multiply(record.getPreClosePrice());
//            stockUpdownDomain.setIncrease(multiply);
//            stockUpdownDomain.setUpDown(multiply.divide(record.getPreClosePrice(),BigDecimal.ROUND_CEILING));
//            BigDecimal divide = record.getMaxPrice().multiply(record.getMinPrice()).divide(record.getPreClosePrice(),BigDecimal.ROUND_CEILING);
//            stockUpdownDomain.setAmplitude(divide);
//            stockUpdownDomain.setTradeAmt(record.getTradeAmount());
//            stockUpdownDomain.setTradeVol(record.getTradeVolume());
//            stockUpdownDomain.setCurDate(curDate);
//            rows.add(stockUpdownDomain);
//        }
//    //封装
//    pageResult.setRows(rows);
//        return R.ok(pageResult);
        return  stockRtInfoService.getStockPageInfo(page,pageSize);
    }

    /**
     * @author nangong
     * @dateTime 2024/10/28 20:23
     * @param
     * @return com.itheima.stock.vo.resp.R<java.util.List<com.itheima.stock.pojo.domain.StockUpdownDomain>>
     * @desp 涨幅小榜
     */
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/stock/increase")
    public R<List<StockUpdownDomain>> getStockIncreasePageInfo(){
        return stockRtInfoService.getStockIncreasePageInfo();
    }


    /**
     * @author nangong
     * @dateTime 2024/10/28 20:41
     * @param
     * @return com.itheima.stock.vo.resp.R<java.util.Map>
     * @desp  统计最新交易日下股票每分钟涨跌停的数量
     */
    @ApiOperation(value = "", notes = "", httpMethod = "GET")
    @GetMapping("/stock/updown/count")
    public R<Map> getStockUpdownCount(){
        return stockRtInfoService.getStockUpdownCount();
    }

    /**
     * 将指定页的股票数据导出到excel表下
     * @param response
     * @param page  当前页
     * @param pageSize 每页大小
     */
    @ApiOperation(value = "将指定页的股票数据导出到excel表下", notes = "将指定页的股票数据导出到excel表下", httpMethod = "GET")
    @GetMapping("/stock/export")
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize){
        stockRtInfoService.stockExport(response,page,pageSize);
    }

    /**
     * 功能描述：统计国内A股大盘T日和T-1日成交量对比功能（成交量为沪市和深市成交量之和）
     * @return
     */
    @GetMapping("/stock/tradeAmt")
    public R<Map> stockTradeVol4InnerMarket(){
        return stockRtInfoService.stockTradeVol4InnerMarket();
    }

    /**
     * 查询当前时间下股票的涨跌幅度区间统计功能
     * 如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询点
     * @return
     */
    @GetMapping("/stock/updown")
    public R<Map> getStockUpDown(){
        return stockRtInfoService.stockUpDownScopeCount();
    }
    /**
     * 功能描述：查询单个个股的分时行情数据，也就是统计指定股票T日每分钟的交易数据；
     *         如果当前日期不在有效时间内，则以最近的一个股票交易时间作为查询时间点
     * @param code 股票编码
     * @return
     */
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> stockScreenTimeSharing(String code){
        return stockRtInfoService.stockScreenTimeSharing(code);
    }
    /**
     * 单个个股日K 数据查询 ，可以根据时间区间查询数日的K线数据
     * @param stockCode 股票编码
     */
    @RequestMapping("/stock/screen/dkline")
    public R<List<Stock4EvrDayDomain>> getDayKLinData(@RequestParam("code") String stockCode){
        return stockRtInfoService.stockCreenDkLine(stockCode);
    }
}
