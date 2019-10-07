-- Utilisation de tables temporaires
WITH temp AS (
  SELECT *
  FROM table
)
SELECT *
FROM temp;

-- DELETE + JOIN
DELETE 
FROM deleted_table D
USING joined_table J
WHERE D.m_product_id = J.m_product_id;
