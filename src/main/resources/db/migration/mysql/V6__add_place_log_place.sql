CREATE TABLE IF NOT EXISTS place_log_place_mapping (
    place_id BIGINT NOT NULL,
    place_log_id BIGINT NOT NULL,
    PRIMARY KEY (place_id, place_log_id),
    FOREIGN KEY (place_id) REFERENCES place (place_id) ON DELETE CASCADE,
    FOREIGN KEY (place_log_id) REFERENCES place_log (place_log_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;