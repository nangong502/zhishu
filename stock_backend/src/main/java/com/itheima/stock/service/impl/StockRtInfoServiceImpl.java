package com.itheima.stock.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.stock.mapper.StockRtInfoMapper;
import com.itheima.stock.pojo.domain.StockUpdownDomain;
import com.itheima.stock.pojo.utils.DateTimeUtil;
import com.itheima.stock.service.IStockRtInfoService;
import com.itheima.stock.pojo.entity.StockRtInfo;
import com.itheima.stock.vo.resp.PageResult;
import com.itheima.stock.vo.resp.R;
import com.itheima.stock.vo.resp.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 个股详情信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-10-24
 */
@Service
@Slf4j
//@RequiredArgsConstructor
public class StockRtInfoServiceImpl extends ServiceImpl<StockRtInfoMapper, StockRtInfo> implements IStockRtInfoService {
    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;
    /**
     * @author nangong
     * @dateTime 2024/10/28 20:23
     * @param page
     * @param pageSize
     * @return com.itheima.stock.vo.resp.R<com.itheima.stock.vo.resp.PageResult<com.itheima.stock.pojo.domain.StockUpdownDomain>>
     * @desp 涨幅全榜
     */
    @Override
    public R<PageResult<StockUpdownDomain>> getStockPageInfo(int page, int pageSize) {
        //时间
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate= DateTime.parse("2022-06-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //查询总条数
        LambdaQueryWrapper<StockRtInfo> wrapper =new LambdaQueryWrapper<>();
        wrapper.eq(StockRtInfo::getCurTime,curDate);
        Long size = Long.valueOf(this.list(wrapper).size());
        //查询
        List<StockUpdownDomain> infos= stockRtInfoMapper.stockRtInfoMapper(curDate,(page-1)*pageSize,pageSize);
        //封装
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(infos);
        pageResult.setTotalRows(size);
        pageResult.setTotalPages(size%pageSize==0?size/pageSize:size/pageSize+1);
        pageResult.setPageNum(Long.valueOf(page));
        pageResult.setPageSize(Long.valueOf(pageSize));
        pageResult.setSize(infos.size());
        return R.ok(pageResult);
    }


    /**
     * @author nangong
     * @dateTime 2024/10/28 20:24
     * @param
     * @return com.itheima.stock.vo.resp.R<java.util.List<com.itheima.stock.pojo.domain.StockUpdownDomain>>
     * @desp 涨幅小榜
     */
    @Override
    public R<List<StockUpdownDomain>> getStockIncreasePageInfo() {
        //时间
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate= DateTime.parse("2022-06-07 15:00:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        List<StockUpdownDomain> list = stockRtInfoMapper.getStockIncreasePageInfo(curDate);
        return R.ok(list);
    }

    /**
     * @return com.itheima.stock.vo.resp.R<java.util.Map>
     * @author nangong
     * @dateTime 2024/10/28 20:41
     * @desp 统计最新交易日下股票每分钟涨跌停的数量
     */
    @Override
    public R<Map> getStockUpdownCount() {
        //1.获取最新的交易时间范围 openTime  curTime
        //1.1 获取最新股票交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        Date curTime = curDateTime.toDate();
        //TODO
        curTime= DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //1.2 获取最新交易时间对应的开盘时间
        DateTime openDate = DateTimeUtil.getOpenDate(curDateTime);
        Date openTime = openDate.toDate();
        //TODO
        openTime= DateTime.parse("2022-01-06 09:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        //2.查询涨停数据
        //约定mapper中flag入参： 1-》涨停数据 0：跌停
        List<Map> upCounts=stockRtInfoMapper.getStockUpdownCount(openTime,curTime,1);
        //3.查询跌停数据
        List<Map> dwCounts=stockRtInfoMapper.getStockUpdownCount(openTime,curTime,0);
        //4.组装数据
        HashMap<String, List> mapInfo = new HashMap<>();
        mapInfo.put("upList",upCounts);
        mapInfo.put("downList",dwCounts);
        //5.返回结果
        return R.ok(mapInfo);
    }

    /**
     * @param response
     * @param page
     * @param pageSize
     * @return void
     * @author nangong
     * @dateTime 2024/10/29 16:02
     * @desp 涨幅榜数据导出
     */
    @Override
    public void stockExport(HttpServletResponse response, Integer page, Integer pageSize) {
        try {
            //1.获取最近最新的一次股票有效交易时间点（精确分钟）
            Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
            //因为对于当前来说，我们没有实现股票信息实时采集的功能，所以最新时间点下的数据
            //在数据库中是没有的，所以，先临时指定一个假数据,后续注释掉该代码即可
            curDate=DateTime.parse("2022-01-05 09:47:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
            //3.查询
            List<StockUpdownDomain> infos= stockRtInfoMapper.stockRtInfoMapper(curDate,(page-1)*pageSize,pageSize);
            //如果集合为空，响应错误提示信息
            if (CollectionUtils.isEmpty(infos)) {
                //响应提示信息
                R<Object> r = R.error(ResponseCode.NO_RESPONSE_DATA);
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(r));
                return;
            }
            //设置响应excel文件格式类型
            response.setContentType("application/vnd.ms-excel");
            //2.设置响应数据的编码格式
            response.setCharacterEncoding("utf-8");
            //3.设置默认的文件名称
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("stockRt", "UTF-8");
            //设置默认文件名称：兼容一些特殊浏览器
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //4.响应excel流
            EasyExcel
                    .write(response.getOutputStream(),StockUpdownDomain.class)
                    .sheet("股票信息")
                    .doWrite(infos);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("当前导出数据异常，当前页：{},每页大小：{},异常信息：{}",page,pageSize,e.getMessage());
        }
    }
}
