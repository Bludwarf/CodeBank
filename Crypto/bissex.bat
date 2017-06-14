@echo off
setlocal enableextensions enabledelayedexpansion

SET /a annee=0
IF [%1] == [] (
	SET /a annee=%DATE:~-4,4%
) ELSE (
	SET /a annee=%1
)

REM divisible par 4 mais pas par 100 ou par 400
SET /a bissextile=0
SET /a div4=%annee% %% 4
IF %div4% EQU 0 (
	SET /a div100=%annee% %% 100
	IF !div100! GTR 0 SET /a bissextile=1
) ELSE (
	SET /a div400=%annee% %% 400
	IF !div400! EQU 0 SET /a bissextile=1
)

ECHO %bissextile%