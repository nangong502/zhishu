package com.itheima.stock.pojo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 国内大盘数据详情表
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("stock_market_index_info")
@Builder
public class StockMarketIndexInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键字段（无业务意义）
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 大盘编码
     */
    private String marketCode;

    /**
     * 指数名称
     */
    private String marketName;

    /**
     * 前收盘点数
     */
    private BigDecimal preClosePoint;

    /**
     * 开盘点数
     */
    private BigDecimal openPoint;

    /**
     * 当前点数
     */
    private BigDecimal curPoint;

    /**
     * 最低点数
     */
    private BigDecimal minPoint;

    /**
     * 最高点数
     */
    private BigDecimal maxPoint;

    /**
     * 成交量(手)
     */
    private Long tradeAmount;

    /**
     * 成交金额（元）
     */
    private BigDecimal tradeVolume;

    /**
     * 当前时间
     */
    private LocalDateTime curTime;


}
