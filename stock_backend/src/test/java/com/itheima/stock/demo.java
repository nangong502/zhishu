package com.itheima.stock;

import cn.hutool.json.JSON;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import com.itheima.stock.pojo.DemoData;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class demo {
    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("小红" + i);
            data.setDate(LocalDate.now());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    @Test
    public void test01(){
        List<DemoData> list = this.data();
        String fileName = "E:\\BaiduNetdiskDownload\\jinrizhishu\\houduan\\stock_parent\\stock_backend\\src\\test\\java\\com\\itheima\\stock\\demo.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
    }


    @Test
    public void test02(){
        List<DemoData> list = new ArrayList<>();
        String fileName = "E:\\BaiduNetdiskDownload\\jinrizhishu\\houduan\\stock_parent\\stock_backend\\src\\test\\java\\com\\itheima\\stock\\demo.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置
        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                list.add(demoData);
            }
        })).sheet().doRead();
        System.out.println(list);
    }
}
