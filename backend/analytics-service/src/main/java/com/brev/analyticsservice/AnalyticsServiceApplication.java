package com.brev.analyticsservice;

import com.brev.analyticsservice.repository.MetricRepository;
import com.brev.core.domain.Metric;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.data.relational.core.dialect.AnsiDialect;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication(scanBasePackages = {"com.brev"},
        exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@Log
public class AnalyticsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsServiceApplication.class, args);
    }

    @Bean
    Dialect dialect() {
        return AnsiDialect.INSTANCE;
    }

    @Bean
    CommandLineRunner initDB(JdbcTemplate jdbcTemplate, @Value("classpath:create-db.sql") Resource createQuery) {
        return args -> {
            jdbcTemplate.execute(createQuery.getContentAsString(Charset.defaultCharset()));
        };
    }

    @Bean
    @Profile("dev")
    CommandLineRunner fillDB(MetricRepository repository) {
        return args -> {
            LocalDateTime currentDateTime = LocalDateTime.now().plusWeeks(1);
            LocalDateTime oneWeekAgo = currentDateTime.minus(2, ChronoUnit.WEEKS);

            List<Metric> metricList = new ArrayList<>();
            while (oneWeekAgo.isBefore(currentDateTime)) {
                int year = oneWeekAgo.getYear();
                int month = oneWeekAgo.getMonthValue();
                int day = oneWeekAgo.getDayOfMonth();
                for (int hour = 0; hour < 24; hour++) {
                    for (int minute = 0; minute < 60; minute++) {
                        Instant instant = LocalDateTime.of(year, month, day, hour, minute, new Random().nextInt(10, 50))
                                .toInstant(ZoneOffset.UTC);
                        for (int i = 0; i < new Random().nextInt(4); i++) {
                            metricList.add(new Metric(instant, "me", "127.0.0.1"));
                        }
                    }
                }
                // Move to the next day
                oneWeekAgo = oneWeekAgo.plusDays(1);
            }
            repository.saveAll(metricList);
        };
    }

}
