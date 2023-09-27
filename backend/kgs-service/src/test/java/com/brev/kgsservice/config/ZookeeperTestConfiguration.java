package com.brev.kgsservice.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.apache.curator.test.Timing;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ZookeeperTestConfiguration {


    @Bean(destroyMethod = "close")
    TestingServer testingServer() throws Exception {
        return new TestingServer();
    }

    @Bean(destroyMethod = "close")
    CuratorFramework curatorFramework(TestingServer server) {
        Timing timing = new Timing();

        CuratorFramework curatorFramework =
                CuratorFrameworkFactory.builder().connectString(server.getConnectString())
                        .sessionTimeoutMs(timing.session())
                        .connectionTimeoutMs(timing.connection())
                        .retryPolicy(new RetryOneTime(1))
                        .build();
        curatorFramework.start();
        return curatorFramework;
    }

}


