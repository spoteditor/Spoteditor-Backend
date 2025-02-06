CREATE TABLE IF NOT EXISTS place_log (
    place_log_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    name VARCHAR(255),
    description TEXT,
    image_url VARCHAR(255),
    address VARCHAR(255),
    road_address VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    sido VARCHAR(255),
    bname VARCHAR(255),
    sigungu VARCHAR(255),
    views BIGINT,
    version BIGINT,
    PRIMARY KEY (place_log_id),
    FOREIGN KEY (user_id) REFERENCES USERS (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;