-- R�cup d'un �l�ment ou cr�ation s'il n'existe pas d�j�
IF element_name IS NULL THEN
	SET @element_id = NULL;
ELSE
	SET @element_id = (SELECT id FROM table WHERE name = element_name);
	IF @element_id IS NULL THEN
		INSERT INTO table (name) VALUES (element_name);
		SET @element_id = LAST_INSERT_ID();
	END IF;
END IF;

-- Ex�cution d'une requ�te sous condition (sans proc�dure stock�e)
SET @exclusiveGroupExists = (SELECT COLUMN_NAME FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA='abtesti' AND TABLE_NAME = 'bidon' AND COLUMN_NAME = 'exclusiveGroup');
SET @lot1 = @exclusiveGroupExists IS NULL;

set @stmt_query = IF(@lot1,
	'ALTER TABLE bidon ADD COLUMN exclusiveGroup INT NULL after id',
	'SELECT "Colonne abtesti.exclusiveGroup d�j� existante" AS message');
prepare stmt from @stmt_query;
EXECUTE stmt;
