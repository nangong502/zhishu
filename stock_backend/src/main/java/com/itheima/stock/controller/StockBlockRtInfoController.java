package com.itheima.stock.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.stock.pojo.domain.StockBlockDomain;
import com.itheima.stock.pojo.entity.StockBlockRtInfo;
import com.itheima.stock.pojo.entity.StockRtInfo;
import com.itheima.stock.pojo.utils.DateTimeUtil;
import com.itheima.stock.service.IStockBlockRtInfoService;
import com.itheima.stock.vo.resp.R;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 股票板块详情信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@RestController
@RequestMapping("/api/quot")
@RequiredArgsConstructor
public class StockBlockRtInfoController {
    private final IStockBlockRtInfoService stockBlockRtInfoService;
    @GetMapping("/sector/all")
    public R<List<StockBlockDomain>> sectorAllLimit(){
        Date lastDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        //mock数据
        lastDate=DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //查询
        LambdaQueryWrapper<StockBlockRtInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StockBlockRtInfo::getCurTime,lastDate);
        wrapper.orderByDesc(StockBlockRtInfo::getTradeVolume);
        wrapper.last("limit 10");
        List<StockBlockRtInfo> list = stockBlockRtInfoService.list(wrapper);
        System.out.println(list);
        //封装
        List<StockBlockDomain> returnlist = new ArrayList<>();
        for (StockBlockRtInfo stockBlockRtInfo : list) {
            StockBlockDomain stockBlockDomain = new StockBlockDomain();
            BeanUtil.copyProperties(stockBlockRtInfo,stockBlockDomain);
            stockBlockDomain.setTradeVol(stockBlockRtInfo.getTradeVolume());
            stockBlockDomain.setTradeAmt(stockBlockRtInfo.getTradeAmount());
            stockBlockDomain.setCode(stockBlockRtInfo.getLabel());
            stockBlockDomain.setName(stockBlockRtInfo.getBlockName());
            stockBlockDomain.setCurDate(DateTime.now().toDate());
            returnlist.add(stockBlockDomain);
        }
        return R.ok(returnlist);
    }
}
