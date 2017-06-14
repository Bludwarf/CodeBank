@echo off
setlocal EnableDelayedExpansion

IF [%1%] == [] (
   ECHO "Usage : gencrypto tb1.mailnotification.bouygtel.fr [https-in]"
   REM %1 : Common Name (CN) pour le futur certificat
   REM %2 : Nom de base du fichier. "https-in" par d�faut
   EXIT /b 1
)

set CN=%1%

set env=prod
set pre=%CN:~0,3%
IF "%pre%"=="tb2" (
    set env=maq
)
IF "%pre%"=="tb1" (
    set env=pprod
)

IF [%2%] == [] (
	set base=https-in-%env%
) ELSE (
	set tmp=%2%
	set base=!tmp!-%env%
)

set csr=%base%.csr
echo CSR ====== %csr%

CALL genkey.bat %*
CALL gencsr.bat %*
CALL gencert.bat %csr%
