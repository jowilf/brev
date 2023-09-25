package com.brev.analyticsservice.repository;

import com.brev.analyticsservice.domain.dto.AggregateType;
import com.brev.analyticsservice.domain.dto.AnalyticsRequest;
import com.brev.analyticsservice.domain.dto.AnalyticsResponse;
import com.brev.analyticsservice.domain.dto.AnalyticsRowMapper;
import com.brev.core.domain.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MetricRepositoryImpl implements MetricRepository {
    private static final String INSERT_QUERY = """
            insert into metrics select col1, col2, col3 
            from input('col1 DateTime, col2 String, col3 String')
            """;

    private static final String ANALYTICS_QUERY_MINUTE = """
            SELECT
                toStartOfInterval(time, INTERVAL ? MINUTE) AS t,
                count(shortUrl) AS count
            FROM metrics
            WHERE  (shortUrl = ?) AND time between ? and ?
            GROUP BY t
            ORDER BY t ASC WITH FILL STEP toIntervalMinute(?)
            LIMIT ?
            """;
    private static final String ANALYTICS_QUERY_HOUR = """
            SELECT
                toStartOfInterval(time, INTERVAL ? HOUR) AS t,
                count(shortUrl) AS count
            FROM metrics
            WHERE  (shortUrl = ?) AND time between ? and ?
            GROUP BY t
            ORDER BY t ASC WITH FILL STEP toIntervalHour(?)
            LIMIT ?
            """;
    private static final String ANALYTICS_QUERY_DAY = """
            SELECT
                toStartOfInterval(time, INTERVAL ? DAY) AS t,
                count(shortUrl) AS count
            FROM metrics
            WHERE  (shortUrl = ?) AND time between ? and ?
            GROUP BY t
            ORDER BY t ASC WITH FILL STEP toIntervalDay(?)
            LIMIT ?
            """;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MetricRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(List<Metric> metrics) {
        jdbcTemplate.batchUpdate(INSERT_QUERY, metrics, metrics.size(), (ps, metric) -> {
            ps.setObject(1, metric.time());
            ps.setObject(2, metric.shortUrl());
            ps.setObject(3, metric.ipAddress());
        });
    }

    @Override
    public List<AnalyticsResponse> getAnalytics(AnalyticsRequest analyticsRequest) {
        String queryStr = ANALYTICS_QUERY_MINUTE;
        if (analyticsRequest.aggregateType() == AggregateType.hour) queryStr = ANALYTICS_QUERY_HOUR;
        else if (analyticsRequest.aggregateType() == AggregateType.day) {
            queryStr = ANALYTICS_QUERY_DAY;
        }
        return jdbcTemplate.query(queryStr, ps -> {
            int i = 0;
            ps.setObject(++i, analyticsRequest.multipleOf());
            ps.setObject(++i, analyticsRequest.shortUrl());
            ps.setObject(++i, analyticsRequest.startDate().withNano(0));
            ps.setObject(++i, analyticsRequest.endDate().withNano(0));
            ps.setObject(++i, analyticsRequest.multipleOf());// interval
            ps.setObject(++i, analyticsRequest.limit());
        }, new AnalyticsRowMapper());
    }
}