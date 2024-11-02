package com.itheima.stock;

import com.itheima.stock.pojo.vo.StockInfoConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.itheima.stock.mapper")
@EnableConfigurationProperties(StockInfoConfig.class)//开启常用参数配置bean
public class JobApp {
    public static void main(String[] args) {
        SpringApplication.run(JobApp.class, args);
    }
}
