package com.itheima.stock.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.stock.pojo.domain.InnerMarketDomain;
import com.itheima.stock.pojo.entity.StockMarketIndexInfo;
import com.itheima.stock.pojo.entity.StockRtInfo;
import com.itheima.stock.pojo.utils.DateTimeUtil;
import com.itheima.stock.pojo.vo.StockInfoConfig;
import com.itheima.stock.service.IStockMarketIndexInfoService;
import com.itheima.stock.service.IStockRtInfoService;
import com.itheima.stock.vo.resp.R;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 国内大盘数据详情表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@RestController
@RequestMapping("/api/quot")
@RequiredArgsConstructor
public class StockMarketIndexInfoController {
    private final StockInfoConfig stockInfoConfig;
    private final IStockMarketIndexInfoService marketIndexInfoService;

    /**
     * @author nangong
     * @dateTime 2024/10/26 19:26
     * @param
     * @return com.itheima.stock.vo.resp.R<java.util.List<com.itheima.stock.pojo.domain.InnerMarketDomain>>
     * @desp 国内指数板块
     */
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> innerIndexAll(){
          //获取最近开盘时间
//        DateTime now = DateTime.now();
//        DateTime lastDate4Stock = DateTimeUtil.getLastDate4Stock(now);
//        LocalDateTime lastLocalDateTime = lastDate4Stock.toLocalDateTime();
//        //mock数据
//        DateTime dateTime = DateTime.now().withYear(2021).withMonthOfYear(12)
//                .withDayOfMonth(28).withHourOfDay(9).withMinuteOfHour(31).withSecondOfMinute(0);
//        LocalDateTime lastLocalDateTime = dateTime.toLocalDateTime();
//        System.out.println(lastLocalDateTime);
        //查询
        LambdaQueryWrapper<StockMarketIndexInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(StockMarketIndexInfo::getMarketCode,stockInfoConfig.getInner());
        wrapper.last("AND cur_time ='2021-12-28 09:31:00'");
        List<StockMarketIndexInfo> list = marketIndexInfoService.list(wrapper);
        //封装
        List<InnerMarketDomain> returnlist = new ArrayList<>();
        for (StockMarketIndexInfo stockMarketIndexInfo : list) {
            InnerMarketDomain marketDomain = new InnerMarketDomain();
            BeanUtils.copyProperties(stockMarketIndexInfo,marketDomain);
            marketDomain.setTradeAmt(stockMarketIndexInfo.getTradeAmount());
            marketDomain.setTradeVol(stockMarketIndexInfo.getTradeVolume());
            marketDomain.setCode(stockMarketIndexInfo.getMarketCode());
            marketDomain.setName(stockMarketIndexInfo.getMarketName());
            BigDecimal sub = stockMarketIndexInfo.getCurPoint().subtract(stockMarketIndexInfo.getPreClosePoint());
            marketDomain.setUpDown(sub);
            marketDomain.setRose(sub.divide(stockMarketIndexInfo.getPreClosePoint(),BigDecimal.ROUND_CEILING));
            BigDecimal subtract = stockMarketIndexInfo.getMaxPoint().subtract(stockMarketIndexInfo.getMinPoint());
            marketDomain.setAmplitude(subtract.divide(stockMarketIndexInfo.getPreClosePoint(),BigDecimal.ROUND_CEILING));
            marketDomain.setCurTime(DateTime.now().toDate());
            returnlist.add(marketDomain);
        }
        return R.ok(returnlist);
    }



}
