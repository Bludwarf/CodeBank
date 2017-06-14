@echo off

IF [%1] == [] (
   ECHO "Usage : gencert https-in-prod.csr"
   EXIT /b 1
)

set WORKSPACE=D:\projets\EXS\workspace
set crypto=%WORKSPACE%\exs-services\lib\Crypto
set csr=%1%
set base=%csr:~0,-4%
set cer=%base%.cer
set /a days=1095

REM année bissextile ?
SET /a annee=%DATE:~-4,4%
bissex %annee% | find "1" 1> NUL && SET bissextile=1

REM année + 1 bissextile ?
SET /a annee=%annee%+1
bissex %annee% | find "1" 1> NUL && SET bissextile=1

REM année + 2 bissextile ?
SET /a annee=%annee%+1
bissex %annee% | find "1" 1> NUL && SET bissextile=1

IF %bissextile% EQU 1 SET /a days=%days%+1
echo Le certificat sera valable %days% jours

openssl x509 -passin pass:bytelexs -in %csr% -out %cer% -days %days% -req -CA %crypto%\exs.capgemini.com.cer -CAkey %crypto%\exs.capgemini.com.key -CAcreateserial -CAserial %crypto%\exs.capgemini.com.srl -sha256 -extfile %crypto%\openssl\openssl.cnf -extensions v3_req