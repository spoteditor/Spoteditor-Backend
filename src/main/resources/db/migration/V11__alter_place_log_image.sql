ALTER TABLE place_log DROP COLUMN image_url;

ALTER TABLE place_log
ADD COLUMN image_id BIGINT,
ADD CONSTRAINT fk_place_log_image FOREIGN KEY (image_id) REFERENCES place_image(image_id) ON DELETE SET NULL;