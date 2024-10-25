package com.itheima.stock.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 个股详情信息表
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_rt_info")
public class StockRtInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键字段（无业务意义）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 股票代码
     */
    private String stockCode;

    /**
     * 股票名称
     */
    private String stockName;

    /**
     * 前收盘价| 昨日收盘价
     */
    private BigDecimal preClosePrice;

    /**
     * 开盘价
     */
    private BigDecimal openPrice;

    /**
     * 当前价格
     */
    private BigDecimal curPrice;

    /**
     * 今日最低价
     */
    private BigDecimal minPrice;

    /**
     * 今日最高价
     */
    private BigDecimal maxPrice;

    /**
     * 成交量
     */
    private Long tradeAmount;

    /**
     * 成交金额
     */
    private BigDecimal tradeVolume;

    /**
     * 当前时间
     */
    private LocalDateTime curTime;


}
