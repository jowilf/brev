CREATE TABLE IF NOT EXISTS metrics
(
    `time` DateTime('UTC'),
    `shortUrl` String,
    `ipAddress` String
)
ENGINE = MergeTree
ORDER BY (time)