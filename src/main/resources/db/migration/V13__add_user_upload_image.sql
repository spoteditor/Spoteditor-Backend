ALTER TABLE users
ADD COLUMN image_id BIGINT,
ADD CONSTRAINT fk_users_image FOREIGN KEY (image_id) REFERENCES place_image(image_id) ON DELETE SET NULL;