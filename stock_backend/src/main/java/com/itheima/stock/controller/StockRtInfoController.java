package com.itheima.stock.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.stock.pojo.domain.InnerMarketDomain;
import com.itheima.stock.pojo.domain.StockBlockDomain;
import com.itheima.stock.pojo.entity.StockBlockRtInfo;
import com.itheima.stock.pojo.entity.StockRtInfo;
import com.itheima.stock.pojo.utils.DateTimeUtil;
import com.itheima.stock.pojo.vo.StockInfoConfig;
import com.itheima.stock.service.IStockRtInfoService;
import com.itheima.stock.vo.resp.R;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 个股详情信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StockRtInfoController {

}
