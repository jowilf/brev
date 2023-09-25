package com.brev.urlservice.domain.document;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.time.OffsetDateTime;

@Getter
@Setter
@Document("urls")
@Sharded(shardKey = "userId")
public class UrlDocument {
    @Id
    private String shortUrl;
    @Field("original_url")
    private String originalUrl;
    @Field("last_visited")
    private OffsetDateTime lastVisited;
    @Field("created_at")
    @CreatedDate
    private OffsetDateTime createdAt;
    @Field("user_id")
    private int userId;
    @Version
    @Field("version")
    private Integer version;
}
