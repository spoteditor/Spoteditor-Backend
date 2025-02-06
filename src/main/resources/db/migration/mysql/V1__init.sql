CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255),
    provider VARCHAR(255),
    name VARCHAR(255),
    image_url VARCHAR(255),
    oauth_user_id VARCHAR(255),
    description TEXT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS place (
    place_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    name VARCHAR(255),
    description TEXT,
    address VARCHAR(255),
    road_address VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    sido VARCHAR(255),
    bname VARCHAR(255),
    sigungu VARCHAR(255),
    category VARCHAR(20),
    bookmark INT,
    version BIGINT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (place_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS place_image (
    image_id BIGINT NOT NULL AUTO_INCREMENT,
    place_id BIGINT,
    original_file VARCHAR(255),
    stored_file VARCHAR(255),
    version BIGINT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (image_id),
    FOREIGN KEY (place_id) REFERENCES place (place_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS bookmark (
    bookmark_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    place_id BIGINT,
    version BIGINT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (bookmark_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (place_id) REFERENCES place (place_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;