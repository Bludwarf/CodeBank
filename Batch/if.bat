REM Utiliser une variable définie dans un THEN
IF %div4% EQU 0 (
	SET /a div100=%annee% %% 100
	IF !div100! GTR 0 SET /a bissextile=1
)