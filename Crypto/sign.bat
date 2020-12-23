@echo off

set csr=%1%
set base=%csr:~0,-4%
set cer=%base%.cer

set ca_dir=..\..\lib\Crypto
set ca=%ca_dir%\exs.capgemini.com
set ca_cer=%ca%.cer
set ca_key=%ca%.key
set ca_srl=%ca%.srl

IF [%1] == [] (
   ECHO Veuillez indiquer le CSR à signer
   EXIT /b 1
)

if not exist %ca_cer% (
	ECHO ERREUR : Vous devez être dans le répertoire Crypto d'un service du workspace EXS : %1
	EXIT /b 2
)

REM Choisir un nombre de jours pour que le jour de fin soit le même que le jour de début (plus simple)
REM Exemple: 1096 days => du 14/01/2016 au 14/01/2019 soit pile 3 ans (et pas 3 ans moins un jour)
set days=1096

openssl x509 -passin pass:bytelexs -in %csr% -out %cer% -days %days% -req -CA %ca_cer% -CAkey %ca_key% -CAcreateserial -CAserial %ca_srl% -sha256 -extfile %ca_dir%\openssl\openssl.cnf -extensions v3_req