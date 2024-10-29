package com.itheima.stock.service;


import com.baomidou.mybatisplus.extension.service.IService;
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
}
