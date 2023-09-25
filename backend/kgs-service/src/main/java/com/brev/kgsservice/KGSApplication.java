package com.brev.kgsservice;

import com.brev.kgsservice.config.AppConfig;
import lombok.extern.java.Log;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Log
public class KGSApplication {

    public static void main(String[] args) {
        SpringApplication.run(KGSApplication.class, args);
    }

    @Bean(destroyMethod = "close")
    SharedCount sharedCount(CuratorFramework curatorFramework, AppConfig appConfig) throws Exception {
        SharedCount counter = new SharedCount(curatorFramework, appConfig.getZookeeperPath(), 0);
        counter.start();
        return counter;
    }
}
