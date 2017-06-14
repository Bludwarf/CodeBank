@echo off

REM exemple : "D:\projets\EXS\workspace\exs-services\Portail WiFi\Crypto"
REM vous pouvez faire un glisser d�poser du dossier d'Eclipse vers la console puis supprimer les guillemets
REM set /p dir=Dossier contenant le CSR:
set dir=D:\projets\EXS\workspace\exs-services\Portail WiFi\Crypto
set base=%dir%\https-in-dpw-maq
set config=%dir%\..\..\lib\Crypto\openssl\openssl.cnf
echo %config%

if not exist "%config%" (
	ECHO ERREUR : Vous devez �tre dans le r�pertoire Crypto d'un service du workspace EXS : %1
	EXIT /b 2
)

openssl req -in "%base%.csr" -out "%base%.cer" -key "%base%.key" -config "%config%" -x509 -days 1095