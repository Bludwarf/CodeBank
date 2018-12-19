-- Utilisation de tables temporaires
WITH temp AS (
  SELECT *
  FROM table
)
SELECT *
FROM temp