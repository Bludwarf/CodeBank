-- Utilisation de tables temporaires (Common Table Expression)
-- ATTENTION : cela empêche les optimisations : https://medium.com/@hakibenita/be-careful-with-cte-in-postgresql-fca5e24d2119
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

-- UPDATE + JOIN
UPDATE A
SET A.c1 = expresion
FROM B
WHERE A.c2 = B.c2;

-- JOIN avec "table constante"
JOIN (VALUES
	('dossier1', 1),
	('dossier2', 2)
) AS selection(dossier, exercice) ON -- ...

-- Fonction fenêtrée : tous les éléments de la partition
avg(salary) OVER (PARTITION BY depname)

-- Fonction fenêtrée avec ORDER BY : tous les éléments déjà parcourus de la partition
rank() OVER (PARTITION BY depname ORDER BY salary DESC)

-- Fonction fenêtrée avec WHERE
rank() FILTER (WHERE depname IS NOT NULL) OVER (PARTITION BY depname ORDER BY salary DESC)

-- https://www.postgresql.org/docs/9.3/functions-window.html

-- Les 4 premières fonctions dépendent du ORDER BY : même valeur = pair => même résultat
-- row_number()	  :
--     Si on souhaite avoir la ligne sans ORDER BY : row_number() OVER (ORDER BY 1)
-- rank()         :
-- dense_rank()   : 
-- percent_rank() :

-- Si on utilise ORDER BY : les fonctions d'aggrégation utilisent les résultats jusqu'à la ligne actuelle
-- Sinon                  : le résultat en prenant toute la partition

-- NOT EXISTS à la place de NOT IN : https://www.red-gate.com/hub/product-learning/sql-prompt/consider-using-not-exists-instead-not-subquery
-- On supprime des résultat à garder tout ce qui existe aussi dans à retirer
SELECT *
FROM garder
WHERE NOT EXISTS (
	SELECT 1 -- Aucune colonne n'est utilisée pour le NOT EXISTS
	FROM retirer
	WHERE garder.id = retirer.id
)
