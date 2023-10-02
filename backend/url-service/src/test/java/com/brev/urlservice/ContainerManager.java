package com.brev.urlservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

@TestConfiguration
public class ContainerManager {
    public final static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"))
                    .withKraft();

    public final static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis/redis-stack-server:7.2.0-v2"));
    public static MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:6.0"));

    static {

        redis.setPortBindings(List.of("6379:6379"));
        redis.start();

        kafka.start();

        mongo.start();

    }


}
