UPDATE tag
SET name = 'SNS 핫플레이스'
WHERE name = 'SNS 갬성샷' AND category = 'MOOD'
    AND NOT EXISTS (
        SELECT * FROM (SELECT 1
                       FROM tag
                       WHERE name = 'SNS 핫플레이스' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '체험 · 액티비티'
WHERE name = '체험·액티비티' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '체험 · 액티비티' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '자연과 함께'
WHERE name = '자리잡고 칠링' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '자연과 함께' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '여유롭게 힐링'
WHERE name = '아웃도어 탐방' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '여유롭게 힐링' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '미술관 · 갤러리'
WHERE name = '미술관·갤러리' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '미술관 · 갤러리' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '알차고 바쁘게!'
WHERE name = '익사이팅 활동' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '알차고 바쁘게!' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '역사가 물든'
WHERE name = '모임장소' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '역사가 물든' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '맛집투어'
WHERE name = '알고간 블루마크' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '맛집투어' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '독서하며 잔잔하게'
WHERE name = '독서하면서 편안하게' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '독서하며 잔잔하게' AND category = 'MOOD') AS TEMP
);

UPDATE tag
SET name = '모임 · 동호회'
WHERE name = '모임·목표지' AND category = 'MOOD'
  AND NOT EXISTS (
    SELECT * FROM (SELECT 1
                   FROM tag
                   WHERE name = '모임 · 동호회' AND category = 'MOOD') AS TEMP
);