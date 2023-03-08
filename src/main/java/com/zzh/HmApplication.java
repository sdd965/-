package com.zzh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
@EnableCaching
public class HmApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmApplication.class, args);
        log.info("项目启动成功...");
    }

}
