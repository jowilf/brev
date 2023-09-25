package com.brev.analyticsservice.domain.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class AnalyticsRowMapper implements RowMapper<AnalyticsResponse> {
    @Override
    public AnalyticsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);

        return new AnalyticsResponse(Instant.from(formatter.parse(rs.getString(1))), rs.getInt(2));
    }
}
