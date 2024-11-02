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

}
